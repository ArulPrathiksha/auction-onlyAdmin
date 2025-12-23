package com.auction.auction_system.controller;

import com.auction.auction_system.entity.Product;
import com.auction.auction_system.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Create a new product
    @PostMapping("/{auctionId}/seller/{sellerId}")
    public ResponseEntity<Product> createProduct(
            @PathVariable Long auctionId,
            @PathVariable Long sellerId,
            @RequestBody Product product) {
        
        Product createdProduct = productService.createProduct(auctionId, sellerId, product);
        return ResponseEntity.ok(createdProduct);
    }

    // Get all products by auction ID
    @GetMapping("/auction/{auctionId}")
    public ResponseEntity<List<Product>> getProductsByAuction(@PathVariable Long auctionId) {
        List<Product> products = productService.getProductsByAuction(auctionId);
        return ResponseEntity.ok(products);
    }

    // Get product by ID
    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProductById(@PathVariable Long productId) {
        Product product = productService.getProductById(productId);
        return ResponseEntity.ok(product);
    }

    // Update a product
    @PutMapping("/{productId}")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long productId,
            @RequestBody Product updatedProduct) {
        
        Product product = productService.updateProduct(productId, updatedProduct);
        return ResponseEntity.ok(product);
    }

    // Delete a product
    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.noContent().build();
    }
}
