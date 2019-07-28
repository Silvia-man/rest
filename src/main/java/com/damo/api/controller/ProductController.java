package com.damo.api.controller;

import com.damo.api.model.Product;
import com.damo.api.service.IProduct;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    IProduct productService;

    @RequestMapping("/")
    public ResponseEntity<String> welcome() {
        return new ResponseEntity<>("Welcome to damo mall", HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> products() {
        List<Product> products = productService.getProducts();
        if(products.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products,HttpStatus.OK);
    }

    @RequestMapping(value = "/product/{id}" , method = RequestMethod.GET)
    public ResponseEntity<Product> getProduct(@PathVariable long id) {
        Product product = productService.getProductById(id);
        if(product == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @RequestMapping(value = "/product", method = RequestMethod.POST)
    public ResponseEntity<Boolean> createProduct(
            @RequestBody Product product, UriComponentsBuilder ucBuilder) {
        if(productService.isProductExist(product.getName())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        productService.addProduct(product);
        HttpHeaders headers= new HttpHeaders();
        headers.setLocation(
                ucBuilder.path("/product/{id}").buildAndExpand(product.getId()).toUri()
        );

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product product) {
        Product current = productService.getProductById(id);
        if(current == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        current.setName(product.getName());
        current.setPrice(product.getPrice());
        current.setCount(product.getCount());

        productService.updateProduct(current);
        return new ResponseEntity<>(current, HttpStatus.OK);
    }

    @RequestMapping(value = "/product/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable long id) {
        Product product = productService.getProductById(id);
        if(product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productService.deleteProduct(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

}
