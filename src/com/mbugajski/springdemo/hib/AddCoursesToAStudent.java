package com.mbugajski.springdemo.hib;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mbugajski.springdemo.hib.entity.Course;
import com.mbugajski.springdemo.hib.entity.Instructor;
import com.mbugajski.springdemo.hib.entity.InstructorDetail;
import com.mbugajski.springdemo.hib.entity.Review;
import com.mbugajski.springdemo.hib.entity.Student;

public class AddCoursesToAStudent {

	public static void main(String[] args) {

		SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
				.addAnnotatedClass(Instructor.class)
				.addAnnotatedClass(InstructorDetail.class)
				.addAnnotatedClass(Course.class)
				.addAnnotatedClass(Review.class)
				.addAnnotatedClass(Student.class)
				.buildSessionFactory();

		Session session = factory.getCurrentSession();

		try {
			session.beginTransaction();
		
			int studentId = 11;
			Student tempStudent = session.get(Student.class, studentId);
			
			System.out.println("\nLoaded student: " + tempStudent.getFirstName() + " "+ tempStudent.getLastName());
			System.out.println("Courses: " + tempStudent.getCourses());
			
			Course tempCourse1 = new Course("Water polo - beginner's guide.");
			Course tempCourse2 = new Course("Factorio - how to stop before your life is consumed.");
			
			tempCourse1.addStudent(tempStudent);
			tempCourse2.addStudent(tempStudent);
			
			System.out.println("\nSaving the courses...");
			session.save(tempCourse1);
			session.save(tempCourse2);
			
			session.getTransaction().commit();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
