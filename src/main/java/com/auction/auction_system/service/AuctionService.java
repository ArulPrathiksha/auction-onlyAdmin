package com.auction.auction_system.service;

import com.auction.auction_system.dto.AuctionRequest;
import com.auction.auction_system.entity.Auction;
import com.auction.auction_system.entity.AuctionStatus;
import com.auction.auction_system.entity.Product;
import com.auction.auction_system.entity.TimeSlot;
import com.auction.auction_system.entity.User;
import com.auction.auction_system.repository.AuctionRepository;
import com.auction.auction_system.repository.ProductRepository;
import com.auction.auction_system.repository.TimeSlotRepository;
import com.auction.auction_system.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private final TimeSlotRepository timeSlotRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public AuctionService(AuctionRepository auctionRepository, TimeSlotRepository timeSlotRepository,
                          ProductRepository productRepository, UserRepository userRepository) {
        this.auctionRepository = auctionRepository;
        this.timeSlotRepository = timeSlotRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Auction createAuction(AuctionRequest req, Long sellerId) {
        // Ensure the selected time slot is available
        TimeSlot slot = timeSlotRepository.findById(req.getTimeSlotId())
                .orElseThrow(() -> new IllegalArgumentException("Slot not found"));

        if (slot.isBooked()) {
            throw new IllegalStateException("Slot already booked");
        }

        // Check for overlapping auctions with the selected time slot
        boolean overlap = auctionRepository.existsOverlapping(slot.getStartTime(), slot.getEndTime());
        if (overlap) {
            throw new IllegalStateException("Another auction overlaps this slot");
        }

        // Ensure the seller exists
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalArgumentException("Seller not found"));

        // Create new auction
        Auction auction = new Auction();
        auction.setTimeSlot(slot);
        auction.getTimeSlot().setStartTime(slot.getStartTime());
        auction.getTimeSlot().setEndTime(slot.getEndTime());
        auction.setStatus(AuctionStatus.UPCOMING);

        // Save the auction
        Auction savedAuction = auctionRepository.save(auction);

        // Mark the time slot as booked
        slot.setBooked(true);
        slot.setAuctionId(savedAuction.getId());
        timeSlotRepository.save(slot);

        // Create and associate products with the auction
        for (Product product : req.getProducts()) {
            product.setAuction(savedAuction);
            product.setSeller(seller);  // Set the seller of the product
            productRepository.save(product);
        }

        return savedAuction;
    }

    // Get all upcoming auctions
    public List<Auction> getUpcoming() {
        return auctionRepository.findByStatus(AuctionStatus.UPCOMING);
    }

    // Get all live auctions
    public List<Auction> getLive() {
        return auctionRepository.findByStatus(AuctionStatus.LIVE);
    }

    // Get all ended auctions
    public List<Auction> getEnded() {
        return auctionRepository.findByStatus(AuctionStatus.ENDED);
    }

    // Get an auction by ID
    public Auction getById(Long id) {
        return auctionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Auction not found"));
    }

    // Mark auction as live
    @Transactional
    public void markLive(Auction auction) {
        if (auction.getStatus() == AuctionStatus.UPCOMING) {
            auction.setStatus(AuctionStatus.LIVE);
            auctionRepository.save(auction);
        }
    }

    // Mark auction as ended and determine the winning bid for each product
    @Transactional
    public void markEnded(Auction auction) {
        if (auction.getStatus() == AuctionStatus.LIVE) {
            auction.setStatus(AuctionStatus.ENDED);

            // Get all products in the auction
            List<Product> products = auction.getProducts();

            for (Product product : products) {
                // Determine the highest bid for this product
                if (product.getWinningBidAmount() != null) {
                    product.setSold(true);
                    System.out.println("Product " + product.getName() + " sold to "
                            + product.getBuyer().getUsername() + " for " + product.getWinningBidAmount());
                    productRepository.save(product);
                }
            }

            auctionRepository.save(auction);
        }
    }
}
