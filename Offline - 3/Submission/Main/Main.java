package Main;

import Entity.Course;
import Entity.ExamScheduler;
import Entity.Student;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        String courseFile = "yor-f-83.crs"; // car-f-92.crs , car-s-91.crs , kfu-s-93.crs , tre-s-92.crs , yor-f-83.crs
        String studentFile = "yor-f-83.stu"; // car-f-92.stu , car-s-91.stu , kfu-s-93.stu , tre-s-92.stu , yor-f-83.stu
        //String outputFile = "car-f-92.sol"; // car-f-92.sol , car-s-91.sol , kfu-s-93.sol , tre-s-92.sol , yor-f-83.sol
        String constructiveHType = "SaturationDegree"; // LargestDegree , SaturationDegree , LargestEnrollment , Random
        //String PerturbativeHType = "KempeChain"; // KempeChain , PairSwap
        String penaltyType = "linear"; // linear , exponential

        ArrayList<Course> courses = readCourseFile(courseFile);
        /*for (Course course : courses) {
            System.out.println(course.getCourseID() + " " + course.getNoOfStudents());
        }*/
        ArrayList<Student> students = readStudentFile(studentFile);
        /*for (Student student : students) {
            for (Course course : student.getCourses()) {
                System.out.print(course.getCourseID() + " ");
            }
            System.out.println();
        }*/
        ExamScheduler examScheduler = new ExamScheduler(students, courses);
        examScheduler.Schedule(constructiveHType, penaltyType);

    }

    public static ArrayList<Course> readCourseFile(String courseFile) throws FileNotFoundException {
        String path = "../datasets/" + courseFile;
        ArrayList<Course> courses = new ArrayList<Course>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] courseInfo = line.trim().split(" ");
                String courseID = courseInfo[0];
                int NoOfStudents = Integer.parseInt(courseInfo[1]);
                Course course = new Course(courseID, NoOfStudents);
                courses.add(course);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courses;
    }

    public static ArrayList<Student> readStudentFile(String studentFile) throws FileNotFoundException {
        String path = "../datasets/" + studentFile;
        ArrayList<Student> students = new ArrayList<Student>();
        BufferedReader br = new BufferedReader(new FileReader(path));
        String line;
        int studentID = 0;
        try {
            while ((line = br.readLine()) != null) {
                String[] courseList = line.trim().split(" ");
                ArrayList<Course> courses = new ArrayList<Course>();
                for(String courseID : courseList){
                    Course course = new Course(courseID);
                    courses.add(course);
                }
                Student student = new Student(studentID, courses);
                students.add(student);
                studentID++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }
}