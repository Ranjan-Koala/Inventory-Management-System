package com.inventory.management.system;

import java.util.List;
import java.util.Scanner;

import com.inventory.management.system.dao.ProductDaoJdbc;
import com.inventory.management.system.model.Category;
import com.inventory.management.system.model.Product;
import com.inventory.management.system.model.Supplier;
import com.inventory.management.system.service.InventoryService;
import com.inventory.management.system.util.HibernateUtil;

public class App {

    public static void main(String[] args) throws Exception {

        InventoryService service = new InventoryService();
        ProductDaoJdbc jdbcDao = new ProductDaoJdbc();
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Inventory Management System ===");

        for (;;) {
            System.out.println();
            System.out.println("1. Add Product");
            System.out.println("2. List All Products");
            System.out.println("3. Add Stock");
            System.out.println("4. Remove Stock");
            System.out.println("5. Show Low Stock (Hibernate)");
            System.out.println("6. Show Low Stock (JDBC)");
            System.out.println("7. Add Category");
            System.out.println("8. List Categories");
            System.out.println("9. Add Supplier");
            System.out.println("10. List Suppliers");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine(); // consume \n

            if (choice == 0) {
                break;
            }

            if (choice == 1) {
                System.out.println("--- Add Product ---");
                System.out.print("Enter SKU: ");
                String sku = sc.nextLine();

                // duplicate check
                Product existing = service.getProductBySku(sku);
                if (existing != null) {
                    System.out.println("Product with this SKU already exists: " + existing);
                } else {
                    System.out.print("Enter Name: ");
                    String name = sc.nextLine();

                    System.out.print("Enter Description: ");
                    String desc = sc.nextLine();

                    System.out.print("Enter Price: ");
                    double price = sc.nextDouble();

                    System.out.print("Enter Quantity: ");
                    int qty = sc.nextInt();
                    sc.nextLine();

                    Product p = new Product(sku, name, price, qty);
                    p.setDescription(desc);

                    // ---- Category assign ----
                    System.out.print("Enter Category ID (or 0 for none): ");
                    long catId = sc.nextLong();
                    sc.nextLine();
                    if (catId != 0) {
                        Category cat = service.getCategoryById(catId);
                        if (cat == null) {
                            System.out.println("No category with id " + catId + ", skipping category.");
                        } else {
                            p.setCategory(cat);
                        }
                    }

                    // ---- Supplier assign ----
                    System.out.print("Enter Supplier ID (or 0 for none): ");
                    long supId = sc.nextLong();
                    sc.nextLine();
                    if (supId != 0) {
                        Supplier sup = service.getSupplierById(supId);
                        if (sup == null) {
                            System.out.println("No supplier with id " + supId + ", skipping supplier.");
                        } else {
                            p.setSupplier(sup);
                        }
                    }

                    service.createProduct(p);
                    System.out.println("Product saved: " + p);
                }
            }

            if (choice == 2) {
                System.out.println("--- All Products ---");
                List<Product> all = service.getAllProducts();
                if (all.isEmpty()) {
                    System.out.println("No products.");
                } else {
                    for (Product p : all) {
                        System.out.println(p);
                    }
                }
            }

            if (choice == 3) {
                System.out.println("--- Add Stock ---");
                System.out.print("Enter Product SKU: ");
                String sku = sc.nextLine();

                Product p = service.getProductBySku(sku);
                if (p == null) {
                    System.out.println("No product found with SKU: " + sku);
                } else {
                    System.out.print("Enter amount to ADD: ");
                    int amt = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter note: ");
                    String note = sc.nextLine();

                    service.addStock(p.getId(), amt, note);
                    System.out.println("Stock updated. New product: " + service.getProductById(p.getId()));
                }
            }

            if (choice == 4) {
                System.out.println("--- Remove Stock ---");
                System.out.print("Enter Product SKU: ");
                String sku = sc.nextLine();

                Product p = service.getProductBySku(sku);
                if (p == null) {
                    System.out.println("No product found with SKU: " + sku);
                } else {
                    System.out.print("Enter amount to REMOVE: ");
                    int amt = sc.nextInt();
                    sc.nextLine();

                    System.out.print("Enter note: ");
                    String note = sc.nextLine();

                    try {
                        service.removeStock(p.getId(), amt, note);
                        System.out.println("Stock updated. New product: " + service.getProductById(p.getId()));
                    } catch (Exception ex) {
                        System.out.println("Error: " + ex.getMessage());
                    }
                }
            }

            if (choice == 5) {
                System.out.println("--- Low Stock via Hibernate ---");
                System.out.print("Enter threshold (e.g. 3): ");
                int th = sc.nextInt();
                sc.nextLine();

                List<Product> low = service.getLowStockProducts(th);
                if (low.isEmpty()) {
                    System.out.println("No low stock products.");
                } else {
                    for (Product p : low) {
                        System.out.println(p);
                    }
                }
            }

            if (choice == 6) {
                System.out.println("--- Low Stock via JDBC ---");
                System.out.print("Enter threshold (e.g. 3): ");
                int th = sc.nextInt();
                sc.nextLine();

                List<Product> low = jdbcDao.findLowStock(th);
                if (low.isEmpty()) {
                    System.out.println("No low stock products.");
                } else {
                    for (Product p : low) {
                        System.out.println(p);
                    }
                }
            }

            if (choice == 7) {
                System.out.println("--- Add Category ---");
                System.out.print("Enter Category Name: ");
                String cname = sc.nextLine();
                System.out.print("Enter Description: ");
                String cdesc = sc.nextLine();

                service.createCategory(cname, cdesc);
                System.out.println("Category saved.");
            }

            if (choice == 8) {
                System.out.println("--- All Categories ---");
                List<Category> categories = service.getAllCategories();
                if (categories.isEmpty()) {
                    System.out.println("No categories.");
                } else {
                    for (Category c : categories) {
                        System.out.println(c);
                    }
                }
            }

            if (choice == 9) {
                System.out.println("--- Add Supplier ---");
                System.out.print("Enter Supplier Name: ");
                String sname = sc.nextLine();
                System.out.print("Enter Contact: ");
                String scontact = sc.nextLine();

                service.createSupplier(sname, scontact);
                System.out.println("Supplier saved.");
            }

            if (choice == 10) {
                System.out.println("--- All Suppliers ---");
                List<Supplier> suppliers = service.getAllSuppliers();
                if (suppliers.isEmpty()) {
                    System.out.println("No suppliers.");
                } else {
                    for (Supplier s : suppliers) {
                        System.out.println(s);
                    }
                }
            }
        }

        sc.close();
        HibernateUtil.shutdown();
        System.out.println("Goodbye!");
    }
}
