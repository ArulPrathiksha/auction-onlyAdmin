package com.auction.auction_system.dto;

import java.time.LocalDateTime;

import com.auction.auction_system.entity.AuctionStatus;

public class AuctionResponse {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private AuctionStatus status;
    public AuctionResponse() {
    }
    public AuctionResponse(Long id, LocalDateTime startTime, LocalDateTime endTime, AuctionStatus status) {
        this.id = id;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    public AuctionStatus getStatus() {
        return status;
    }
    public void setStatus(AuctionStatus status) {
        this.status = status;
    }
    
}
