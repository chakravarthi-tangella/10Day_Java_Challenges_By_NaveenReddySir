package com.productmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.productmanagement.entity.Product;
import com.productmanagement.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	ProductService service;
	
//	Retrieving all the products
	
	@GetMapping("/products")
	public List<Product> getAllProducts()
	{
		return service.getAllProducts();
	}
	
//	Retrieving product based on product name
	
	@GetMapping("/product/{name}")
	public Product getProduct(@PathVariable String name)
	{
		return service.getProductByName(name);
	}
	
// Fetching all the matching products based on given text
	
	@GetMapping("/product/searchByText/{text}")
	public List<Product> getAllProductsByText(@PathVariable String text)
	{
		return service.getProductsByText(text);
	}
	
// Fetching products based on place they are stored
	
	@GetMapping("/product/searchByPlace/{place}")
	public List<Product> getAllProductsByPlace(@PathVariable String place)
	{
		return service.getProductsByText(place);
	}
	
// Fetching products which are currently out of Warranty
	@GetMapping("/product/outOfWarranty")
	public List<Product> getAllProductsOutOfWarranty()
	{
		return service.getProductsOutOfWarranty();
	}
	
//	Adding product to database
	
	@PostMapping("/product")
	public void addProduct(@RequestBody Product product)
	{
		service.addProduct(product);
	}
	
	
}
