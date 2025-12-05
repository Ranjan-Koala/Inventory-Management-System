package com.inventory.management.system.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.inventory.management.system.model.Product;
import com.inventory.management.system.util.HibernateUtil;

public class ProductDaoHibernate implements ProductDao {

    private final SessionFactory sf = HibernateUtil.getSessionFactory();

    @Override
    public Product findById(Long id) {
        try (Session s = sf.openSession()) {
            return s.get(Product.class, id);
        }
    }

    @Override
    public Product findBySku(String sku) {
        try (Session s = sf.openSession()) {
            String hql = "from Product p where p.sku = :sku";
            Query<Product> q = s.createQuery(hql, Product.class);
            q.setParameter("sku", sku);
            return q.uniqueResult();
        }
    }

    @Override
    public List<Product> findAll() {
        try (Session s = sf.openSession()) {
            String hql = "from Product";
            Query<Product> q = s.createQuery(hql, Product.class);
            return q.getResultList();
        }
    }

    @Override
    public void save(Product p) {
        Transaction tx = null;
        try (Session s = sf.openSession()) {
            tx = s.beginTransaction();
            s.persist(p);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void update(Product p) {
        Transaction tx = null;
        try (Session s = sf.openSession()) {
            tx = s.beginTransaction();
            s.merge(p);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }

    @Override
    public void delete(Product p) {
        Transaction tx = null;
        try (Session s = sf.openSession()) {
            tx = s.beginTransaction();
            s.remove(p);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw e;
        }
    }
}
