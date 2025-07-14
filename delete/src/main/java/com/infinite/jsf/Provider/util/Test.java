package com.infinite.jsf.Provider.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Test {
	public static void main(String[] args) {
	
	 SessionFactory factory=SessionHelper.getSessionFactory();
	 Session session=factory.openSession();
	 
	 System.out.println("connection is : "+ session);
}
}
