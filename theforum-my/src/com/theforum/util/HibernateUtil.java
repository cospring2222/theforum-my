package com.theforum.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 *
 * @author Uliana and David
 */
public class HibernateUtil {

	private static HibernateUtil instance = new HibernateUtil();
	private  SessionFactory sessionFactory;

	private HibernateUtil() {
		this.sessionFactory = buildSessionFactory();
	}

	private synchronized static SessionFactory buildSessionFactory() {
		Configuration configuration = new Configuration().configure();
		try {
			StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties());
			return configuration.buildSessionFactory(builder.build());

		} catch (Throwable ex) {
			// Log the exception.
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static HibernateUtil getInstance() {
	    if(instance == null){
	        return new HibernateUtil();
	    }
	    return instance;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static Session beginTransaction() {
		Session hibernateSession = HibernateUtil.getSession();
		hibernateSession.beginTransaction();
		return hibernateSession;
	}

	public static void commitTransaction() {
		HibernateUtil.getSession().getTransaction().commit();
	}

	public static void rollbackTransaction() {
		HibernateUtil.getSession().getTransaction().rollback();
	}

	public static void closeSession() {
		HibernateUtil.getSession().close();
	}

	public static Session getSession() {
		Session hibernateSession = getInstance().getSessionFactory().getCurrentSession();
		return hibernateSession;
	}
}

