package Comparator;

import Entity.Course;

public class RandomComp implements java.util.Comparator<Course> {
    @Override
    public int compare(Course c1, Course c2) {
        return (int) (Math.random() * 3) - 1;
    }
}
