package com.viet.controller;

import com.viet.exception.ProductException;
import com.viet.model.Product;
import com.viet.model.Seller;
import com.viet.request.CreateProductRequest;
import com.viet.service.Impl.ProductService;
import com.viet.service.Impl.SellerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers/products")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SellerProductController {

    ProductService productService;
    SellerService sellerService;

    @GetMapping
    public ResponseEntity<List<Product>> getProductBySellerId(@RequestHeader("Authorization") String jwt) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);

        log.info("getProductBySellerId: {}", seller);

        List<Product> products = productService.getProductBySellerId(seller.getId());

        log.info("products: {}", products);

        return new ResponseEntity<>(products, HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<Product> createProduct(
            @RequestHeader("Authorization") String jwt,
            @RequestBody CreateProductRequest req
    ) throws Exception {
        Seller seller = sellerService.getSellerProfile(jwt);
        Product product = productService.createProduct(req, seller);

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId){
        try {
            productService.deleteProduct(productId);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch (ProductException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long productId, @RequestBody Product product) throws ProductException {

            Product updateProduct = productService.updateProduct(productId, product);
            return new ResponseEntity<>(updateProduct,HttpStatus.OK);

    }


}
