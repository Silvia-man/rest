package com.damo.api.service;

import com.damo.api.model.Product;

import java.util.List;

public interface IProduct {
    List<Product> getProducts();
    Product getProductById(long id);
    Product getProductByName(String name);

    boolean addProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(long id);
    boolean isProductExist(String name);
}
