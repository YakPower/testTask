package com.example.testTask.Entities.DTO;

import java.util.HashMap;
import java.util.Map;

public class ReportDTO {
    private String supplier;
    private Map<String, ProductData> products = new HashMap<>();
    private double totalCost;
    private double totalQuantity;

    public ReportDTO(String supplier) {
        this.supplier = supplier;
    }

    public void addProductCost(String productName, double cost) {
        products.computeIfAbsent(productName, k -> new ProductData()).addCost(cost);
        totalCost += cost;
    }

    public void addProductQuantity(String productName, double quantity) {
        products.computeIfAbsent(productName, k -> new ProductData()).addQuantity(quantity);
        totalQuantity += quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public Map<String, ProductData> getProducts() {
        return products;
    }

    public void setProducts(Map<String, ProductData> products) {
        this.products = products;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(double totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

}

class ProductData {
    private double totalCost;
    private double quantity;

    public void addCost(double cost) {
        totalCost += cost;
    }

    public void addQuantity(double quantity) {
        this.quantity += quantity;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
}
