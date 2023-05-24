package com.productmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.productmanagement.entity.Product;
import com.productmanagement.repository.ProductDB;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

	@Autowired
	ProductDB db;

	// Fetching all the products from products list
	public List<Product> getAllProducts() {
		List<Product> list = db.findAll();
		if (list.size() > 0)
			return list;
		else
			return new ArrayList<Product>();

	}

	// Fetching product details by id
	public Product getProductByid(int id) {
		Optional<Product> product = db.findById(id);
		return product.get();
	}

	// Adding or Updating Products to List
	public Product addOrUpdateProduct(Product product) {
		if (product.getId() == -1) {
			product = db.save(product);
		} else {
			Optional<Product> p = db.findById(product.getId());
			if (p.isPresent()) {
				Product updatedProduct = p.get();
				updatedProduct.setName(product.getName());
				updatedProduct.setType(product.getType());
				updatedProduct.setPlace(product.getPlace());
				updatedProduct.setWarranty(product.getWarranty());
				return updatedProduct;
			} else {
				product = db.save(product);
				return product;
			}
		}
		return product;
	}

	// Deleting Product based on given id
	public void deleteProductById(int id) {
		Optional<Product> product = db.findById(id);

		if (product.isPresent()) {
			db.deleteById(id);
		}
	}

	// Fetching product based on product name
	public Product getProductByName(String name) {
		return db.findByName(name);
	}

	// Fetching all the matching products based on given text
	public List<Product> getProductsByText(String text) {
		return db.findByNameIgnoreCaseContainingOrTypeIgnoreCaseContainingOrPlaceIgnoreCaseContaining(text, text, text);
	}

	// Fetching products based on place they are stored
	public List<Product> getProductsByPlace(String text) {
		return db.findByPlace(text);
	}

	// Fetching products which are currently out of Warranty
	public List<Product> getProductsOutOfWarranty() {
		int year = Year.now().getValue();
		return db.findByWarrantyLessThan(year);
	}

}
