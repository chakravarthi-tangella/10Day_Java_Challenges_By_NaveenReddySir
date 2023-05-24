package com.productmanagement.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.productmanagement.entity.Product;
import com.productmanagement.service.ProductService;

@Controller
@RequestMapping("/")
public class ProductController {
	
	@Autowired
	ProductService service;
	
	@GetMapping("/")
	public String index()
	{
		return "index";
	}
	
//	Retrieving all the products
	
	@RequestMapping(path = "/products")
	public String getAllProducts(Model model)
	{
		List<Product> list = service.getAllProducts();
		model.addAttribute("products", list);
		return "allProductsPage";
	}
	
//	Adding product to database
	
	@RequestMapping(value = "/product",method = RequestMethod.POST)
	public String addProduct(Product product)
	{
		service.addOrUpdateProduct(product);
		return "success";
	}
	
	
//	Edit Product details
	
	@RequestMapping(path = {"/edit", "/edit/{id}"})
	public String editProductById(Model model, @PathVariable("id") Optional<Integer> id)
	{
		if(id.isPresent())
		{
			Product p = service.getProductByid(id.get());
			model.addAttribute("product", p);
		}
		else
		{
			model.addAttribute("product", new Product());
		}
		return "addProductPage";
	}
	
//	Delete Product Details
	@RequestMapping(path = "/delete/{id}")
	public String deleteEmployeeById(Model model, @PathVariable("id") int id)
	{
		service.deleteProductById(id);
		return "index";
	}

//	Retrieving product based on product name
//	
//	@GetMapping("/product/{name}")
//	public Product getProduct(@PathVariable String name)
//	{
//		return service.getProductByName(name);
//	}
	
// Fetching all the matching products based on given text
	
	@RequestMapping(value = "/searchProductsWithText",method = RequestMethod.GET)
	public String getAllProductsByText()
	{
		return "searchByTextPage";
	}

	@RequestMapping(path = "/product/searchByText/{text}")
	public String getAllProductsByText(Model model, @PathVariable("text") String text)
	{
		List<Product> list = service.getProductsByText(text);
		model.addAttribute("products", list);
		return "allProductsPage";
	}

// Fetching products based on place they are stored
	
	@RequestMapping(value = "/searchProductsWithPlace",method = RequestMethod.GET)
	public String getAllProductsByPlace()
	{
		return "searchByPlacePage";
	}

	@RequestMapping(path = "/product/searchByPlace/{place}")
	public String getAllProductsByPlace(Model model, @PathVariable("place") String place)
	{
		List<Product> list = service.getProductsByText(place);
		model.addAttribute("products", list);
		return "allProductsPage";
	}

//// Fetching products which are currently out of Warranty

	@RequestMapping(path = "/product/outOfWarranty")
	public String getAllProductsOutOfWarranty(Model model)
	{
		List<Product> list = service.getProductsOutOfWarranty();
		model.addAttribute("products", list);
		return "allProductsPage";
	}
	
//	@GetMapping("/product/outOfWarranty")
//	public List<Product> getAllProductsOutOfWarranty()
//	{
//		return service.getProductsOutOfWarranty();
//	}	
	
}
