package com.inventory.management.system.service;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.inventory.management.system.dao.ProductDao;
import com.inventory.management.system.dao.ProductDaoHibernate;
import com.inventory.management.system.exceptions.InsufficientStockException;
import com.inventory.management.system.model.Category;
import com.inventory.management.system.model.Product;
import com.inventory.management.system.model.StockTransaction;
import com.inventory.management.system.model.Supplier;
import com.inventory.management.system.model.TxType;
import com.inventory.management.system.util.HibernateUtil;

public class InventoryService {

    private final ProductDao productDao;
    private final SessionFactory sessionFactory;

    public InventoryService() {
        this.productDao = new ProductDaoHibernate();
        this.sessionFactory = HibernateUtil.getSessionFactory();
    }

    // ========= PRODUCT METHODS =========

    // create and save new product
    public void createProduct(Product product) {
        productDao.save(product);
    }

    public List<Product> getAllProducts() {
        return productDao.findAll();
    }

    public Product getProductById(Long id) {
        return productDao.findById(id);
    }

    public Product getProductBySku(String sku) {
        return productDao.findBySku(sku);
    }

    // increase stock
    public void addStock(Long productId, int amount, String note) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Product product = session.get(Product.class, productId);
            if (product == null) {
                throw new RuntimeException("Product not found with id: " + productId);
            }

            Integer currentQty = product.getQuantity();
            if (currentQty == null) {
                currentQty = 0;
            }

            product.setQuantity(currentQty + amount);
            session.merge(product);

            // Stock IN transaction
            StockTransaction st = new StockTransaction();
            st.setProduct(product);
            st.setChangeAmount(amount);
            st.setType(TxType.IN);
            st.setNote(note);
            st.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            session.persist(st);

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            session.close();
        }
    }

    // decrease stock
    public void removeStock(Long productId, int amount, String note) {
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            Product product = session.get(Product.class, productId);
            if (product == null) {
                throw new RuntimeException("Product not found with id: " + productId);
            }

            Integer currentQty = product.getQuantity();
            if (currentQty == null) {
                currentQty = 0;
            }

            if (currentQty < amount) {
                throw new InsufficientStockException(
                        "Not enough stock for product id " + productId +
                                ". Current: " + currentQty + ", required: " + amount);
            }

            product.setQuantity(currentQty - amount);
            session.merge(product);

            // Stock OUT transaction
            StockTransaction st = new StockTransaction();
            st.setProduct(product);
            st.setChangeAmount(amount);
            st.setType(TxType.OUT);
            st.setNote(note);
            st.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            session.persist(st);

            tx.commit();
        } catch (RuntimeException ex) {
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            session.close();
        }
    }

    // utility: find low stock products using HQL
    public List<Product> getLowStockProducts(int threshold) {
        Session session = sessionFactory.openSession();
        try {
            String hql = "from Product p where p.quantity < :th";
            Query<Product> query = session.createQuery(hql, Product.class);
            query.setParameter("th", threshold);
            return query.getResultList();
        } finally {
            session.close();
        }
    }

    // small report: total inventory value (sum of price * quantity)
    public double getTotalInventoryValue() {
        List<Product> products = productDao.findAll();
        double total = 0.0;
        for (Product p : products) {
            if (p.getPrice() != null && p.getQuantity() != null) {
                total = total + (p.getPrice() * p.getQuantity());
            }
        }
        return total;
    }

    // ========= CATEGORY METHODS =========

    public Category createCategory(String name, String description) {
        Category c = new Category(name, description);

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(c);
            tx.commit();
            return c;
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    public List<Category> getAllCategories() {
        Session session = sessionFactory.openSession();
        try {
            String hql = "from Category";
            Query<Category> q = session.createQuery(hql, Category.class);
            return q.getResultList();
        } finally {
            session.close();
        }
    }

    public Category getCategoryById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Category.class, id);
        } finally {
            session.close();
        }
    }

    // ========= SUPPLIER METHODS =========

    public Supplier createSupplier(String name, String contact) {
        Supplier s = new Supplier(name, contact);

        Session session = sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.persist(s);
            tx.commit();
            return s;
        } catch (RuntimeException ex) {
            if (tx != null) tx.rollback();
            throw ex;
        } finally {
            session.close();
        }
    }

    public List<Supplier> getAllSuppliers() {
        Session session = sessionFactory.openSession();
        try {
            String hql = "from Supplier";
            Query<Supplier> q = session.createQuery(hql, Supplier.class);
            return q.getResultList();
        } finally {
            session.close();
        }
    }

    public Supplier getSupplierById(Long id) {
        Session session = sessionFactory.openSession();
        try {
            return session.get(Supplier.class, id);
        } finally {
            session.close();
        }
    }
}
