package com.mbugajski.springdemo.hib;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mbugajski.springdemo.hib.entity.Course;
import com.mbugajski.springdemo.hib.entity.Instructor;
import com.mbugajski.springdemo.hib.entity.InstructorDetail;
import com.mbugajski.springdemo.hib.entity.Review;
import com.mbugajski.springdemo.hib.entity.Student;

public class GetCoursesForAStudent {

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
		
			int studentId = 12;
			Student tempStudent = session.get(Student.class, studentId);
			
			System.out.println("\nLoaded student: " + tempStudent.getFirstName() + " "+ tempStudent.getLastName());
			if (tempStudent.getCourses().size() > 0) {
				System.out.println("Courses: ");
				for (Course course : tempStudent.getCourses()) {
					System.out.println(" - " + course.getTitle());
				}
			}
			else {
				System.out.println(tempStudent.getFirstName() + " " + tempStudent.getLastName() + " has no active courses");
			}
			
			session.getTransaction().commit();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
