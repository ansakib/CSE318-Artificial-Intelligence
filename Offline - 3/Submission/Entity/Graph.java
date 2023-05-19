package Entity;

import java.util.ArrayList;

public class Graph {
    private ArrayList<Course> courses;

    public Graph(ArrayList<Course> courses) {
        setCourses(courses);
    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    private void setCourses(ArrayList<Course> courses) {
        this.courses = courses;
    }

    public Course getCourseByID(String courseID) {
        for(Course course : courses) {
            if(course.getCourseID().equals(courseID)) {
                return course;
            }
        }
        return null;
    }

    public void buildGraph(ArrayList<Student> students) {
        for (Student student : students) {
            for (Course course : student.getCourses()) {
                for (Course course2 : student.getCourses()) {
                    if (!course.equals(course2)) {
                        Course temp1 = getCourseByID(course.getCourseID());
                        Course temp2 = getCourseByID(course2.getCourseID());
                        temp1.addConflictingCourse(temp2);
                        temp2.addConflictingCourse(temp1);
                    }
                }
            }
        }
    }
}
