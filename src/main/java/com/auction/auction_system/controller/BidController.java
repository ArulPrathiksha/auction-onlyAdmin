package com.auction.auction_system.controller;

import com.auction.auction_system.service.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/bids")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @PostMapping
    public ResponseEntity<String> placeBid(@RequestParam Long productId, @RequestParam Long userId, @RequestParam BigDecimal amount) {
        try {
            bidService.placeBid(productId, userId, amount);
            return ResponseEntity.ok("Bid placed successfully");
        } catch (IllegalArgumentException | IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}
