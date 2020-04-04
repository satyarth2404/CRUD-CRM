package com.hib.saty;

import com.hib.saty.Entity.*;
import java.io.*;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class MainApp {
	public static void main(String[] args)throws IOException {
		
		SessionFactory factory = new Configuration()
									.configure("hibernate.cfg.xml")
									.addAnnotatedClass(Employee.class)
									.buildSessionFactory();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("===== Welcome to Employee CRUD Application =====");
		
		try {
		Session session = factory.getCurrentSession();
		
		//==============CREATE FUNCTIONALITY================= 
		
		System.out.println("===== SAVING OBJECTS =====");
		
		//start a transaction
		session.beginTransaction();
		
		System.out.println("Enter number of employees you want to save");
		int num = Integer.parseInt(br.readLine());
		for(int i=1;i<=num;i++) {
		//Create an employee
		System.out.println("Enter first name of employee "+i);
		String fname = br.readLine();
		System.out.println("Enter last name of employee "+i);
		String lname = br.readLine();
		System.out.println("Enter company of employee "+i);
		String company = br.readLine();
		Employee emp = new Employee(fname,lname,company);
		session.save(emp);
		}
		session.getTransaction().commit();
		
		System.out.println("==== Employees saved in Database ====");
		System.out.println();
		
		//==========CREATE FUNCTIONALITY END===========
		
		
		//===========READ FUNCTIONALITY==================
		
		System.out.println("==== RETRIEVE OBJECTS BY PRIMARY KEY ====");
		System.out.println("Enter employee id of employee you want to search for: ");
		int id = Integer.parseInt(br.readLine());
		session = factory.getCurrentSession();
		session.beginTransaction();
		System.out.println("Searching employees with id ="+id);
		List<Employee> emplist = session.createQuery("from Employee e where e.id = '"+id+"'").getResultList();
		displayEmployees(emplist);
		
		System.out.println("==== RETRIEVE OBJECTS BY COMPANY NAME ====");
		System.out.println("Enter company whose employee you want to search for ");
		String comp = br.readLine();
		System.out.println("Searching employees of company ="+comp);
		emplist = session.createQuery("from Employee e where company = '"+comp+"'").getResultList();
		displayEmployees(emplist);
		session.getTransaction().commit();
		System.out.println();
		
		//============ READ FUNCTIONALITY END =============
		
		// =========== DELETE FUNCTIONALITY ===============
		System.out.println("==== DELETING EMPLOYEE BY PRIMARY KEY ====");
		session = factory.getCurrentSession();
		session.beginTransaction();
		System.out.println("Enter the primary key of the employee you want to delete :");
		int eid = Integer.parseInt(br.readLine());
		session.createQuery("delete from Employee where id = '"+eid+"'").executeUpdate();
		session.getTransaction().commit();
		System.out.println("Employee with id ="+eid+" deleted from record");
		
		// ========== DELETE FUNCTIONALITY END ============
		
		}
		finally {
			factory.close();
		}
	}

	private static void displayEmployees(List<Employee> emplist) {
		for(Employee emp: emplist) {
			System.out.println(emp.toString());
		}
	}
}
