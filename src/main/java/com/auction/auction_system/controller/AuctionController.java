package com.auction.auction_system.controller;

import com.auction.auction_system.dto.AuctionRequest;
import com.auction.auction_system.dto.AuctionResponse;
import com.auction.auction_system.entity.Auction;
import com.auction.auction_system.entity.Product;
import com.auction.auction_system.service.AuctionService;
import com.auction.auction_system.service.BidService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping("/upcoming")
    public List<AuctionResponse> upcoming() {
        return auctionService.getUpcoming().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/live")
    public List<AuctionResponse> live() {
        return auctionService.getLive().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/ended")
    public List<AuctionResponse> ended() {
        return auctionService.getEnded().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody AuctionRequest req, @RequestParam Long sellerId) {
        Auction auction = auctionService.createAuction(req, sellerId);
        return ResponseEntity.ok(toResponse(auction));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        Auction auction = auctionService.getById(id);
        return ResponseEntity.ok(toResponse(auction));
    }

    private AuctionResponse toResponse(Auction auction) {
        return new AuctionResponse(auction.getId(), auction.getStartTime(), auction.getEndTime(), auction.getStatus());
    }
}
