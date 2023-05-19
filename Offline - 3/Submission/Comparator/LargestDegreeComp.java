package Comparator;

import Entity.Course;

public class LargestDegreeComp implements java.util.Comparator<Course> {
    @Override
    public int compare(Course c1, Course c2) {
        int c1Degree = c1.getConflictingCourses().size();
        int c2Degree = c2.getConflictingCourses().size();
        if(c1Degree > c2Degree) {
            return -1;
        }
        else if(c1Degree < c2Degree) {
            return 1;
        }
        else {
            return 0;
        }
    }
}

