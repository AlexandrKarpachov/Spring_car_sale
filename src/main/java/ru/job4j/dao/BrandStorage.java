package ru.job4j.dao;

import java.util.List;
import java.util.function.Function;

import javax.persistence.Query;


import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import ru.job4j.domain.Brand;

@Component
public class BrandStorage {
	private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	//private final static Logger LOG = Logger.getLogger(CarStoreageDB.class.getName());
	

	public void addBrand(Brand brand) {
		tx(session -> session.save(brand));
	}


	public void update(Brand brand) {
		tx(session -> {
			session.update(brand);
			return true;
		});
	}


	public void delete(Brand brand) {
		tx(session -> {
			session.delete(brand);
			return true;
		});
	}

	@SuppressWarnings("unchecked")

	public List<Brand> getAll() {
		return tx(session -> session.createQuery("from Brand b order by b.name").list());
	}


	public Brand getById(long id) {
		final Session session = factory.openSession();
	    final Transaction tx = session.beginTransaction();
	    Brand result = null;
	    try {
	        result = session.get(Brand.class, id);
	        tx.commit();
	        Hibernate.initialize(result.getModels()); 
	    } catch (final Exception e) {
	    	// LOG.error("Can't get Brand by id= " + id, e);
	    } finally {
	        session.close();
	    }
	    return result;
	}

	@SuppressWarnings("unchecked")
	public Brand getByName(String name) {
		Brand result = null;
		final Session session = factory.openSession();
	    final Transaction tx = session.beginTransaction();
	    try {
	    	Query query = session.createQuery("from Brand where name = :brandName");
			query.setParameter("brandName", name);
			result = (Brand) query.getResultList()
					.stream().findFirst().orElse(null);
			tx.commit();
			Hibernate.initialize(result.getModels()); 
	    } catch (final Exception e) {
	    	 //LOG.error("Can't get Brand by name= " + name, e);
	    } finally {
	        session.close();
	    }
	    return result;
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
	        //LOG.error(e);
	    } finally {
	        session.close();
	    }
	    return null;
	}
}
