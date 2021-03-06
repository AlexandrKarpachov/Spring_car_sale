package ru.job4j.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Component;

import ru.job4j.domain.Car;
import ru.job4j.domain.CarFilter;

@Component
public class CarStoreageDB {
	private static final CarStoreageDB INSTANCE = new CarStoreageDB();
	private final SessionFactory factory = new Configuration().configure().buildSessionFactory();
	//private final static Logger LOG = Logger.getLogger(CarStoreageDB.class.getName());
	private CarStoreageDB() {
	}
	
	public static CarStoreageDB getInstance() {
		return INSTANCE;
	}
	

	public void save(Car car) {
		this.tx(session -> session.save(car));
		
	}

	public void update(Car car) {
		this.tx(session -> {
			session.update(car);
			return true;
			}
		);
	}

	public void delete(Car car) {
		this.tx(session -> {
			session.delete(car);
			return true;
			}
		);
		
	}

	public Car getById(long id) {
		return this.tx(session -> session.get(Car.class, id));
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

	public Long getPagesCount(CarFilter filter) {
		Long result = 0L;
		try (Session session = factory.openSession()) {
			CriteriaBuilder builder = session.getCriteriaBuilder();
			CriteriaQuery<Long> query = builder.createQuery(Long.class);
			Root<Car> root = query.from(Car.class);
			CriteriaQuery<Long> select = query.select(builder.count(root));
			List<Predicate> predicates = filter.getFilterList(builder, root);
			select.where(predicates.toArray(new Predicate[] {}));
			TypedQuery<Long> typedQuery = session.createQuery(select);
			Long rows = typedQuery.getSingleResult();
			int rowInPage = filter.getMaxResultSize();
			result = rows % rowInPage > 0 ? (rows / rowInPage) + 1 : rows / rowInPage;
		} catch (Exception e) {
			//LOG.error("can't compute page count", e);
		}
		return result;
	}
	
	public List<Car> getByFilter(CarFilter filter) {
		List<Car> rst = new ArrayList<>();
		try (Session session = factory.openSession()) {                 
		CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Car> query = builder.createQuery(Car.class);
        Root<Car> root = query.from(Car.class);
        List<Predicate> predicates = filter.getFilterList(builder, root);
        query.select(root).where(predicates.toArray(new Predicate[]{}));
		query.orderBy(builder.asc(root.get("created")));
		rst = session.createQuery(query)
        		.setFirstResult(filter.getPage() * filter.getMaxResultSize())
        		.setMaxResults(filter.getMaxResultSize())
        		.getResultList();
		} catch (Exception e) {
			//LOG.error("can't fetch car list", e);
		}
		return rst;
	}
}
