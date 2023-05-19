package Heuristics;

import Entity.Course;
import Entity.Graph;
import Entity.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Queue;

public class PerturbativeHeuristics {

    String penaltyType;
    public PerturbativeHeuristics(String penaltyType) {
        this.penaltyType = penaltyType;
    }

    public double kempeSwap(Graph graph, ArrayList<Student> students) {
        double initialPenalty = getPenalty(graph, students);
        for(int i=0; i<1000; i++){
            int randomIndex = (int)(Math.random() * (graph.getCourses().size()-1));
            Course slot1Course = graph.getCourses().get(randomIndex);
            int timeSlot1 = slot1Course.getTimeSlot();
            ArrayList<Course> conflictingCourses = slot1Course.getConflictingCourses();
            if(conflictingCourses.size() <= 0){
                continue;
            }
            int randomIndex2 = (int)(Math.random() * (conflictingCourses.size()-1));
            Course slot2Course = conflictingCourses.get(randomIndex2);
            int timeSlot2 = slot2Course.getTimeSlot();

            if(timeSlot1 == timeSlot2){
                continue;
            }

            int replacementSlot = timeSlot2;
            HashSet<Course> kempeChain = new HashSet<>();
            ArrayList<Course> subgraph = new ArrayList<>();
            HashMap<String, Boolean> visited = new HashMap<>();

            for(Course course: graph.getCourses()){
                visited.put(course.getCourseID(), false);
            }

            subgraph.add(slot1Course);
            kempeChain.add(slot1Course);
            visited.put(slot1Course.getCourseID(), true);

            while (!subgraph.isEmpty()){
                Course selectedCourse = subgraph.remove(0);
                if(selectedCourse.getTimeSlot() == timeSlot1){
                    replacementSlot = timeSlot2;
                }
                else if(selectedCourse.getTimeSlot() == timeSlot2){
                    replacementSlot = timeSlot1;
                }

                ArrayList<Course> selectedConfCourses = selectedCourse.getConflictingCourses();

                for(Course course: selectedConfCourses){
                    if(!visited.get(course.getCourseID()) && course.getTimeSlot() == replacementSlot){
                        visited.put(course.getCourseID(), true);
                        subgraph.add(course);
                        kempeChain.add(course);
                    }
                }

            }

            for(Course course: kempeChain){
                Course courseInGraph = graph.getCourseByID(course.getCourseID());
                if(course.getTimeSlot() == timeSlot1){
                    courseInGraph.setTimeSlot(timeSlot2);
                }
                else if(course.getTimeSlot() == timeSlot2){
                    courseInGraph.setTimeSlot(timeSlot1);
                }
            }

            double newPenalty = getPenalty(graph, students);

            if(newPenalty >= initialPenalty){
                for(Course course: kempeChain){
                    Course courseInGraph = graph.getCourseByID(course.getCourseID());
                    if(course.getTimeSlot() == timeSlot1){
                        courseInGraph.setTimeSlot(timeSlot2);
                    }
                    else if(course.getTimeSlot() == timeSlot2){
                        courseInGraph.setTimeSlot(timeSlot1);
                    }
                }
            }
            else{
                initialPenalty = newPenalty;
            }

        }
        return initialPenalty;
    }

    public double pairswap(Graph graph, ArrayList<Student> students){
        double initialPenalty = getPenalty(graph, students);

        for(int i=0; i<1000; i++){
            int randomIndex = (int)(Math.random() * (graph.getCourses().size()-1));
            Course slot1Course = graph.getCourses().get(randomIndex);
            int timeSlot1 = slot1Course.getTimeSlot();
            int randomIndex2 = (int)(Math.random() * (graph.getCourses().size()-1));
            Course slot2Course = graph.getCourses().get(randomIndex2);
            int timeSlot2 = slot2Course.getTimeSlot();

            if(timeSlot1 == timeSlot2){
                continue;
            }

            boolean isPairSwapPossible1 = true;
            boolean isPairSwapPossible2 = true;

            for(Course course: slot1Course.getConflictingCourses()){
                if(course.getTimeSlot() == timeSlot2 && !course.getCourseID().equals(slot2Course.getCourseID())){
                    isPairSwapPossible1 = false;
                    break;
                }
            }

            for(Course course: slot2Course.getConflictingCourses()){
                if(course.getTimeSlot() == timeSlot1 && !course.getCourseID().equals(slot1Course.getCourseID())){
                    isPairSwapPossible2 = false;
                    break;
                }
            }

            boolean isPairSwapPossible = isPairSwapPossible1 && isPairSwapPossible2;

            if(isPairSwapPossible){
                slot1Course.setTimeSlot(timeSlot2);
                slot2Course.setTimeSlot(timeSlot1);
                double newPenalty = getPenalty(graph, students);
                if(newPenalty >= initialPenalty){
                    slot1Course.setTimeSlot(timeSlot1);
                    slot2Course.setTimeSlot(timeSlot2);
                }
                else{
                    initialPenalty = newPenalty;
                }
            }

        }

        return initialPenalty;
    }

    public double getPenalty(Graph graph, ArrayList<Student> students) {
        double penalty = 0;

        for(Student s : students){
            ArrayList<Course> courses = s.getCourses();
            if(courses.size() < 2){
                continue;
            }
            ArrayList<Integer> timeSlots = new ArrayList<>();
            for(Course c : courses){
                Course temp = graph.getCourseByID(c.getCourseID());
                timeSlots.add(temp.getTimeSlot());
            }
            int tempPenalty = 0;
            for(int i = 0; i < timeSlots.size() -1 ; i++){
                for(int j = i + 1; j < timeSlots.size(); j++){
                    if(timeSlots.get(i)!=-1 && timeSlots.get(j)!=-1){
                        int gap = Math.abs(timeSlots.get(i) - timeSlots.get(j));
                        //add 2^gap to penalty
                        if(gap>=1 && gap<=5) {
                            if(penaltyType.equalsIgnoreCase("linear")){
                                tempPenalty += (2*(5 - gap));
                            }
                            else if(penaltyType.equalsIgnoreCase("exponential")){
                                tempPenalty += Math.pow(2, 5 - gap);
                            }
                        }
                    }
                }
            }
            penalty += tempPenalty;
        }

        penalty = penalty/(double) students.size();

        return penalty;
    }

}
