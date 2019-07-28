package com.damo.api.service;

import com.damo.api.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Productimpl implements IProduct {

    private static List<Product> products;

    static {
        products = initDate();
    }

    private static List<Product> initDate() {
        List<Product> products = new ArrayList<>();
        products.add(new Product(1, "iPhone", 8000.0, 1000));
        products.add(new Product(2, "RedMi k20", 2999.9, 2000 ));
        products.add(new Product(3, "Mi CC", 4000.0, 2000));
        products.add(new Product(4, "HuaWei p30", 5000.0, 3000));
        products.add(new Product(5, "iPhoneXS Max", 8900.0, 4000));
        return products;
    }

    @Override
    public List<Product> getProducts() {
        return products;
    }

    @Override
    public Product getProductById(long id) {
        for(Product p:products) {
            if(p.getId() == id){
                return p;
            }
        }
        return null;
    }

    @Override
    public Product getProductByName(String name) {
        for(Product p:products) {
            if(p.getName().equalsIgnoreCase(name)){
                return p;
            }
        }
        return null;
    }

    @Override
    public boolean addProduct(Product product) {
        products.add(product);
        return true;
    }

    @Override
    public boolean updateProduct(Product product) {
        int index = products.indexOf(product);
        products.set(index, product);
        return false;
    }

    @Override
    public boolean deleteProduct(long id) {
        for (Product p: products) {
            if(p.getId() == id) {
                products.remove(p);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isProductExist(String name) {
        return getProductByName(name) != null;
    }
}
