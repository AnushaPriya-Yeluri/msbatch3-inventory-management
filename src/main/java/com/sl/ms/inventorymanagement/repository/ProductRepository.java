package com.sl.ms.inventorymanagement.repository;

import org.springframework.data.repository.CrudRepository;
import com.sl.ms.inventorymanagement.model.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {	

}
