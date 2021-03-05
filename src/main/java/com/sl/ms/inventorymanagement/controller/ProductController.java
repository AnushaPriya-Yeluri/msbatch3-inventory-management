package com.sl.ms.inventorymanagement.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sl.ms.inventorymanagement.exceptions.ProductNotFoundException;
import com.sl.ms.inventorymanagement.model.Product;
import com.sl.ms.inventorymanagement.repository.ProductRepository;


@RestController
@RequestMapping("products")
public class ProductController {

	private final ProductRepository repository;

	public ProductController(ProductRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public Iterable<Product> getProducts() {
		return repository.findAll();
	}	
	
	@GetMapping("{product_id}")
	public Product getProduct(@PathVariable int product_id) {
		return repository.findById(product_id).orElseThrow(ProductNotFoundException::new);
	}	

	@PostMapping("/{product_id}")
	public Product addProduct(@RequestBody Product newProduct, @PathVariable int product_id) {
		 
	      Product product = new Product();
		 	product.setId(product_id);
	        product.setName(newProduct.getName());
	        product.setQuantity(newProduct.getQuantity());
	        product.setPrice(newProduct.getPrice());
	        return repository.save(product);
	      
	}
		
	@PostMapping
	public void addMultipleProduct(@RequestBody List<Product> productList) {
		repository.saveAll(productList);
	}

	
	@PutMapping("/{product_id}")
	public Product updateProduct(@RequestBody Product newProduct, @PathVariable int product_id) {
	    
	    return repository.findById(product_id)
	      .map(product -> {
	        product.setName(newProduct.getName());
	        product.setQuantity(newProduct.getQuantity());
	        product.setPrice(newProduct.getPrice());
	        return repository.save(product);
	      })
	      .orElseGet(() -> {
	        newProduct.setId(product_id);
	        return repository.save(newProduct);
	      });
	}
	 
	@DeleteMapping("/{product_id}")
	public void deleteOrder(@PathVariable int product_id) {
		repository.findById(product_id).orElseThrow(ProductNotFoundException::new);
		repository.deleteById(product_id);
	}
	
}
