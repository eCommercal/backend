package com.viet.service.Impl;

import com.viet.exception.ProductException;
import com.viet.model.Product;
import com.viet.model.Seller;
import com.viet.request.CreateProductRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    Product createProduct(CreateProductRequest request, Seller seller);
    void deleteProduct(Long id) throws ProductException;
    Product updateProduct(Long id, Product product) throws ProductException;
    Page<Product> getAllProducts(String category, String brand, String colors, String sizes, Integer minPrice, Integer maxPrice,
            Integer minDiscount, String sort, String stock, Integer pageNumber);
    List<Product> searchProducts(String query);
    Product findProductById(Long id) throws ProductException;
    List<Product> getProductBySellerId(Long sellerId);

}
