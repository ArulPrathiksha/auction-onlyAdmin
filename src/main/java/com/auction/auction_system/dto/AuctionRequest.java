package com.auction.auction_system.dto;

import java.util.List;

import com.auction.auction_system.entity.Product;


public class AuctionRequest {
    private Long timeSlotId;
    private List<Product> products;
    
    public AuctionRequest() {
    }
    
    public AuctionRequest(Long timeSlotId, List<Product> products) {
        this.timeSlotId = timeSlotId;
        this.products = products;
    }

    public Long getTimeSlotId() {
        return timeSlotId;
    }
    public void setTimeSlotId(Long timeSlotId) {
        this.timeSlotId = timeSlotId;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    
}

