package ru.job4j.dao;

import ru.job4j.domain.User;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface  UserRepository extends CrudRepository<User, Long> {
	User findByLogin(String login);
}
