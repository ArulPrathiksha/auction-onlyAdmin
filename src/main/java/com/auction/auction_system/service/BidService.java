package com.auction.auction_system.service;

import com.auction.auction_system.entity.Participant;
import com.auction.auction_system.entity.Product;
import com.auction.auction_system.entity.User;
import com.auction.auction_system.repository.ParticipantRepository;
import com.auction.auction_system.repository.ProductRepository;
import com.auction.auction_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BidService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;

    public BidService(ProductRepository productRepository , UserRepository userRepository , ParticipantRepository participantRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.participantRepository = participantRepository;
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

        // Check if the user is registered for the auction
        Participant participant = participantRepository.findByAuctionIdAndUserId(product.getAuction().getId(), userId)
                .orElseThrow(() -> new IllegalStateException("User must be registered for the auction to place a bid"));

        // Set the new highest bid
        product.setWinningBidAmount(amount);
        product.setBidPlacedTime(java.time.LocalDateTime.now());

        User bidder = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        product.setBuyer(bidder);

        productRepository.save(product);
    }
}
