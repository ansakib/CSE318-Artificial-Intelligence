package Heuristics;

import Entity.Course;
import Entity.Graph;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import Comparator.*;

public class ConstructiveHeuristics {
    private String type;
    private Comparator comparator;

    public ConstructiveHeuristics(String type) {
        this.type = type;
        setComparator(type);
    }

    private void setComparator(String type) {
        if(type.equalsIgnoreCase("LargestDegree")){
            comparator = new LargestDegreeComp();
        }
        else if(type.equalsIgnoreCase("SaturationDegree")){
            comparator = new SaturationDegreeComp();
        }
        else if(type.equalsIgnoreCase("LargestEnrollment")){
            comparator = new LargestEnrollComp();
        }
        else if(type.equalsIgnoreCase("Random")){
            comparator = new RandomComp();
        }
    }

    private boolean isConflictSlot(Course course, int timeSlot){
        for(Course conflictingCourse : course.getConflictingCourses()){
            if(conflictingCourse.getTimeSlot() == timeSlot){
                return true;
            }
        }
        return false;
    }

    public int allotTimeSlots(Graph graph){
        int totalCourses = graph.getCourses().size();
        int totalTimeSlots = 0;
        PriorityQueue<Course> priorityQueue = new PriorityQueue<Course>(totalCourses, comparator);
        priorityQueue.addAll(graph.getCourses());

        Course topCourse = priorityQueue.poll();
        if(topCourse==null){
            System.out.println("No courses to allot time slots");
            return -1;
        }
        topCourse.setTimeSlot(totalTimeSlots);

        ArrayList<Course> poppedCourses = new ArrayList<Course>();

        while(!priorityQueue.isEmpty()){
            Course course = priorityQueue.poll();
            poppedCourses.add(course);
            if(course.getTimeSlot() == -1){
                int tempSlot = 0;
                boolean isConf = true;
                for(tempSlot = 0; tempSlot <= totalTimeSlots; tempSlot++){
                    isConf = isConflictSlot(course, tempSlot);
                    if(!isConf){
                        course.setTimeSlot(tempSlot);
                        break;
                    }
                }

                if(isConf){
                    totalTimeSlots++;
                    course.setTimeSlot(totalTimeSlots);
                }

                if(type.equalsIgnoreCase("SaturationDegree")){
                    priorityQueue.clear();

                    priorityQueue.addAll(graph.getCourses());
                    priorityQueue.removeAll(poppedCourses);
                }

            }
        }
        return totalTimeSlots+1;
    }
}
