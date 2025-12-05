package com.inventory.management.system.dao;

import java.util.List;
import com.inventory.management.system.model.Product;

public interface ProductDao {

    Product findById(Long id);

    Product findBySku(String sku);

    List<Product> findAll();

    void save(Product product);

    void update(Product product);

    void delete(Product product);
}
