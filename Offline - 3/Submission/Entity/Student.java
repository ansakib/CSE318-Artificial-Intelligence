package Entity;

import java.util.ArrayList;

public class Student {
    private int studentID;
    private ArrayList<Course> courses = new ArrayList<Course>();
    public Student(int studentID, ArrayList<Course> courses) {
        setStudentID(studentID);
        setCourses(courses);
    }
    public int getStudentID() {
        return studentID;
    }
    private void setStudentID(int studentID) {
        this.studentID = studentID;
    }
    public ArrayList<Course> getCourses() {
        return courses;
    }
    private void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }
}
