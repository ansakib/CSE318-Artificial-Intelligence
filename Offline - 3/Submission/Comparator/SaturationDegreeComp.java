package Comparator;
import Entity.Course;

public class SaturationDegreeComp implements java.util.Comparator<Course> {
    @Override
    public int compare(Course c1, Course c2) {
        int c1SatDegree = c1.getSaturationDegree();
        int c2SatDegree = c2.getSaturationDegree();
        if(c1SatDegree > c2SatDegree) {
            return -1;
        }
        else if(c1SatDegree < c2SatDegree) {
            return 1;
        }
        else {
            int c1Degree = c1.getConflictingCourses().size();
            int c2Degree = c2.getConflictingCourses().size();
            return Integer.compare(c2Degree, c1Degree);
        }
    }
}
