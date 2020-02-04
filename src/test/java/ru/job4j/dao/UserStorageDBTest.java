package ru.job4j.dao;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import ru.job4j.domain.User;


public class UserStorageDBTest {

	@Test
	public void testAddUser() {
		 try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
	               "spring-context.xml"
	        )) {
			 UserStorageDB importer = context.getBean(UserStorageDB.class);
			 User user = new User();
			 user.setLogin("login");
			 user.setName("na");
			 user.setSurname("sur");
			 user.setPassword("pas");
			 user.setPhone("555");
			 importer.addUser(user);
			 
			 
			 System.out.println(importer.getUserByLogin("login"));
		 }
	}

}
