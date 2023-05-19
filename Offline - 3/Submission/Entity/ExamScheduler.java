package Entity;

import Heuristics.ConstructiveHeuristics;
import Heuristics.PerturbativeHeuristics;

import java.util.ArrayList;

public class ExamScheduler {
    private ArrayList<Student> students;
    private ArrayList<Course> courses;

    public ExamScheduler(ArrayList<Student> students, ArrayList<Course> courses) {
        this.students = students;
        this.courses = courses;
    }

    public void Schedule(String constructiveHType, String penaltyType) {
        Graph graph = new Graph(courses);
        graph.buildGraph(students);

        ConstructiveHeuristics constHr = new ConstructiveHeuristics(constructiveHType);

        int totalTimeSlots = constHr.allotTimeSlots(graph);
        System.out.println("Total Time Slots: " + totalTimeSlots);

        //checkConsistency(graph, students);

        PerturbativeHeuristics pertHr = new PerturbativeHeuristics(penaltyType);
        double orgPenalty = pertHr.getPenalty(graph, students);
        System.out.println("Original Penalty: " + orgPenalty);

        double kempePenalty = pertHr.kempeSwap(graph, students);
        System.out.println("Penalty after kempe-swap: " + kempePenalty);


        //checkConsistency(graph, students);

        double pairswapPenalty = pertHr.pairswap(graph, students);
        System.out.println("Penalty after pair-swap: " + pairswapPenalty);


    }

    public void checkConsistency(Graph graph, ArrayList<Student> students){
        for(Student student : students){
            ArrayList<Integer> slots = new ArrayList<>();
            for(Course course : student.getCourses()){
                Course temp = graph.getCourseByID(course.getCourseID());
                if(!slots.contains(temp.getTimeSlot())){
                    slots.add(temp.getTimeSlot());
                }
                else{
                    System.out.println("Inconsistent");
                    return;
                }
            }
        }
    }
}
