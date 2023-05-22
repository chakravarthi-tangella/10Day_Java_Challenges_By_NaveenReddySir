package com.chakri.ProductManagementUsingSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Year;
import java.util.List;

@Component
public class ProductService {

    @Autowired
    ProductDB db;

    // Adding Products to List
    public void addProduct(Product product)
    {
        db.save(product);
    }

    // Fetching all the products from products list
    public List<Product> getAllProducts()
    {
        return db.findAll();
    }

    // Fetching product based on product name
    public Product getProductByName(String name)
    {
        return db.findByName(name);
    }

    // Fetching all the matching products based on given text
    public List<Product> getProductsByText(String text)
    {
        return db.findByNameIgnoreCaseContainingOrTypeIgnoreCaseContainingOrPlaceIgnoreCaseContaining(text,text,text);
    }

    // Fetching products based on place they are stored
    public List<Product> getProductsByPlace(String text)
    {
        return db.findByPlace(text);
    }

    // Fetching products which are currently out of Warranty
    public List<Product> getProductsOutOfWarranty()
    {
        int year = Year.now().getValue();
        return db.findByWarrantyLessThan(year);
    }

}
