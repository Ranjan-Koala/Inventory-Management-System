package com.inventory.management.system.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.inventory.management.system.model.Product;
import com.inventory.management.system.util.JdbcUtil;

public class ProductDaoJdbc {

    public List<Product> findLowStock(int threshold) throws SQLException {
        String sql = "SELECT id, sku, name, description, price, quantity " +
                     "FROM product WHERE quantity < ?";

        List<Product> result = new ArrayList<>();

        try (Connection c = JdbcUtil.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, threshold);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Product p = new Product();
                    p.setId(rs.getLong("id"));
                    p.setSku(rs.getString("sku"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setPrice(rs.getDouble("price"));
                    p.setQuantity(rs.getInt("quantity"));

                    result.add(p);
                }
            }
        }
        return result;
    }
}
