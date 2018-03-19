package com.theforum.dao;

/**
*
 * @author Uliana and David
*/

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import org.hibernate.Query;

public interface GenericDAO<T, ID extends Serializable> {

   public void save(T entity);

   public void merge(T entity);

   public void delete(T entity);

   public List<T> findMany(Query query);

   public T findOne(Query query);

   public List findAll(Class<T> classname);

   public T findByID(Class<T> classname, Long id);
}
