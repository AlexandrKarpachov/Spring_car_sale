package ru.job4j;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import ru.job4j.domain.TestService;
import ru.job4j.domain.User;


public class Test {
	//private SearchCriteria criteria;
	public static void main(String[] args) {
		try (ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
                "spring-data.xml"
        )) {
	        TestService serv = context.getBean( TestService.class);
	        User us = serv.getUser("root");
	       System.out.println(us);
	 }
	}
}
