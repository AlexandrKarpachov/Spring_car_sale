package ru.job4j.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ru.job4j.dao.UserRepository;

@Component
public class TestService {
	@Autowired
	UserRepository repo;
	
	public User getUser(String login) {
		return repo.findByLogin(login);
	}
	
	
}
