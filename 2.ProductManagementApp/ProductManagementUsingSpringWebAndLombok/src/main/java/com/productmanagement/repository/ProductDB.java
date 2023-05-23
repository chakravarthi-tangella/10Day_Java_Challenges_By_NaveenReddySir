package com.productmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.productmanagement.entity.Product;

import java.util.List;

@Repository
public interface ProductDB extends JpaRepository<Product,Integer> {
    Product findByName(String name);
    List<Product> findByNameIgnoreCaseContainingOrTypeIgnoreCaseContainingOrPlaceIgnoreCaseContaining(String name, String type, String place);
    List<Product> findByPlace(String place);
    List<Product> findByWarrantyLessThan(int year);
}
