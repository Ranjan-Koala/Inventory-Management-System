package com.inventory.management.system.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import com.inventory.management.system.model.Product;
import com.inventory.management.system.model.Category;
import com.inventory.management.system.model.Supplier;
import com.inventory.management.system.model.StockTransaction;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = build();

    private static SessionFactory build() {
        try {
            Configuration cfg = new Configuration().configure();

            // Register annotated entity classes
            cfg.addAnnotatedClass(Product.class);
            cfg.addAnnotatedClass(Category.class);
            cfg.addAnnotatedClass(Supplier.class);
            cfg.addAnnotatedClass(StockTransaction.class);

            return cfg.buildSessionFactory();

        } catch (Throwable ex) {
            System.err.println("SessionFactory creation failed: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        getSessionFactory().close();
    }
}
