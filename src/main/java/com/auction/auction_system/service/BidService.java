package com.auction.auction_system.service;

import com.auction.auction_system.entity.Product;
import com.auction.auction_system.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BidService {

    private final ProductRepository productRepository;

    public BidService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void placeBid(Long productId, Long userId, BigDecimal amount) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Check if the product is sold or not
        if (product.isSold()) {
            throw new IllegalStateException("Product already sold");
        }

        // Check if the bid is higher than the current highest bid
        BigDecimal currentHighestBid = product.getWinningBidAmount();
        if (currentHighestBid != null && amount.compareTo(currentHighestBid) <= 0) {
            throw new IllegalArgumentException("Bid must be higher than the current highest bid");
        }

        // Set the new highest bid
        product.setWinningBidAmount(amount);
        product.setBidPlacedTime(java.time.LocalDateTime.now());

        // Optionally, you can associate the user who placed the winning bid (optional)
        // product.setBuyer(user);

        productRepository.save(product);
    }
}
