package com.auction.auction_system.service;

import com.auction.auction_system.entity.Auction;
import com.auction.auction_system.entity.Product;
import com.auction.auction_system.entity.User;
import com.auction.auction_system.repository.AuctionRepository;
import com.auction.auction_system.repository.ProductRepository;
import com.auction.auction_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final AuctionRepository auctionRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository, AuctionRepository auctionRepository ) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.auctionRepository = auctionRepository;
    }

    @Transactional
    public Product createProduct(Long auctionId, Long sellerId, Product product) {
        // Ensure the seller exists
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));

        // Ensure the auction exists
        Auction auction = auctionRepository.findById(auctionId)
            .orElseThrow(() -> new IllegalArgumentException("Auction not found"));
        // Set the seller and auction to the product
        product.setSeller(seller);
        product.setAuction(auction);  // Link to the auction

        // Save the product
        return productRepository.save(product);
    }

    public List<Product> getProductsByAuction(Long auctionId) {
        return productRepository.findByAuctionId(auctionId);
    }

    public Product getProductById(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
    }

    @Transactional
    public Product updateProduct(Long productId, Product updatedProduct) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        existingProduct.setName(updatedProduct.getName());
        existingProduct.setDescription(updatedProduct.getDescription());
        existingProduct.setPrice(updatedProduct.getPrice());
        // Other fields can be updated as necessary

        return productRepository.save(existingProduct);
    }

    @Transactional
    public void deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        productRepository.delete(product);
    }
}
