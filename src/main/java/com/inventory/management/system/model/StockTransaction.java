package com.inventory.management.system.model;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "stock_transaction")
public class StockTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "changeAmount")
    private Integer changeAmount;

    @Enumerated(EnumType.STRING)
    private TxType type;   // IN or OUT

    private String note;

    @Column(name = "created_at")
    private Timestamp createdAt;

    public Long getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(Integer changeAmount) {
        this.changeAmount = changeAmount;
    }

    public TxType getType() {
        return type;
    }

    public void setType(TxType type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "StockTransaction{id=" + id +
                ", product=" + (product != null ? product.getId() : null) +
                ", changeAmount=" + changeAmount +
                ", type=" + type +
                ", note='" + note + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
