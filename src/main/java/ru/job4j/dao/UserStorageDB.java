package ru.job4j.dao;

import java.util.function.Function;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import ru.job4j.domain.User;

@Component
public class UserStorageDB {
	private static final UserStorageDB INSTANCE = new UserStorageDB();
	private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	
	private UserStorageDB() {
	}
	
	public static UserStorageDB getInstance() {
		return INSTANCE;
	}
	
	public void addUser(User user) {
		tx(session -> session.save(user));
		
	}

	public void deleteUser(User user) {
		tx(session -> {
			session.delete(user);
			return true;
		});
		
	}

	public void updateUser(User user) {
		tx(session -> {
			session.update(user);
			return true;
		});
		
	}

	public User getUserById(long id) {
		return tx(session -> session.get(User.class, id));			
	}

	@SuppressWarnings("unchecked")
	public User getUserByLogin(String login) {
		return (User) tx(session -> session.createQuery("from User where login = :userLogin")
									.setParameter("userLogin", login)
									.getResultList()
									.stream().findFirst().orElse(null));
	}
	
	private <T> T tx(final Function<Session, T> command) {
	    final Session session = factory.openSession();
	    final Transaction tx = session.beginTransaction();
	    try {
	        T rsl = command.apply(session);
	        tx.commit();
	        return rsl;
	    } catch (final Exception e) {
	        session.getTransaction().rollback();
	        throw e;
	    } finally {
	        session.close();
	    }
	}
	
}
