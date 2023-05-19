package Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Course {
    private String courseID;
    private int NoOfStudents;
    private int timeSlot;
    private ArrayList<Course> conflictingCourses;
    public Course(String courseID, int NoOfStudents) {
        setCourseID(courseID);
        setNoOfStudents(NoOfStudents);
        setTimeSlot(-1);
        conflictingCourses = new ArrayList<Course>();
    }
    public Course(String courseID){
        setCourseID(courseID);
        setNoOfStudents(0);
        setTimeSlot(-1);
        conflictingCourses = new ArrayList<Course>();
    }
    public String getCourseID() {
        return courseID;
    }
    private void setCourseID(String courseID) {
        this.courseID = courseID;
    }
    public int getNoOfStudents() {
        return NoOfStudents;
    }
    private void setNoOfStudents(int NoOfStudents) {
        this.NoOfStudents = NoOfStudents;
    }
    public int getTimeSlot() {
        return timeSlot;
    }
    public void setTimeSlot(int timeSlot) {
        this.timeSlot = timeSlot;
    }

    public ArrayList<Course> getConflictingCourses() {
        return conflictingCourses;
    }

    public void addConflictingCourse(Course course) {
        if(!conflictingCourses.contains(course)) {
            conflictingCourses.add(course);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Course) {
            Course course = (Course) obj;
            return course.getCourseID().equals(this.getCourseID());
        }
        return false;
    }

    public int getSaturationDegree(){
        Set<Integer> timeSlots = new HashSet<Integer>();
        for(Course course : conflictingCourses){
            if(course.getTimeSlot() != -1){
                timeSlots.add(course.getTimeSlot());
            }
        }
        return timeSlots.size();
    }
}
