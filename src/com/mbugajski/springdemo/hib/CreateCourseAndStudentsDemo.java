package com.mbugajski.springdemo.hib;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.mbugajski.springdemo.hib.entity.Course;
import com.mbugajski.springdemo.hib.entity.Instructor;
import com.mbugajski.springdemo.hib.entity.InstructorDetail;
import com.mbugajski.springdemo.hib.entity.Review;
import com.mbugajski.springdemo.hib.entity.Student;

public class CreateCourseAndStudentsDemo {

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
		
			int theId = 1;
			Instructor tempInstructor = session.get(Instructor.class, theId);
			
			Course tempCourse = new Course("EVE Offline - Spreadsheets for Fun and Profit");

			Student tempStudent1 = new Student ("John", "Doe", "jd@jd.com");
			Student tempStudent2 = new Student ("Mia", "Moe", "mm@mm.com");
			Student tempStudent3 = new Student ("Alex", "Jin", "aj@aj.com");
			
			tempInstructor.add(tempCourse);

			session.save(tempCourse);
			
			tempCourse.addStudent(tempStudent1);
			tempCourse.addStudent(tempStudent2);
			tempCourse.addStudent(tempStudent3);
			
			session.save(tempStudent1);
			session.save(tempStudent2);
			session.save(tempStudent3);
			
			session.getTransaction().commit();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			session.close();
			factory.close();
		}
	}

}
