package Comparator;

import Entity.Course;

public class LargestEnrollComp implements java.util.Comparator<Course> {
    @Override
    public int compare(Course c1, Course c2) {
        int c1Enroll = c1.getNoOfStudents();
        int c2Enroll = c2.getNoOfStudents();
        return Integer.compare(c2Enroll, c1Enroll);
    }
}
