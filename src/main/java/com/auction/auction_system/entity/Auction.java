package com.auction.auction_system.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "auctions")
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "time_slot_id", nullable = false, unique = true)
    private TimeSlot timeSlot;

    @OneToMany(mappedBy = "auction", fetch = FetchType.EAGER)
    private List<Product> products;

    public Auction() {
    }

    public Auction(Long id, AuctionStatus status, TimeSlot timeSlot) {
        this.id = id;
        this.status = status;
        this.timeSlot = timeSlot;
    }

    public Auction(Long id, AuctionStatus status, TimeSlot timeSlot, List<Product> products) {
        this.id = id;
        this.status = status;
        this.timeSlot = timeSlot;
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public AuctionStatus getStatus() {
        return status;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(AuctionStatus status) {
        this.status = status;
    }

    public void setTimeSlot(TimeSlot timeSlot) {
        this.timeSlot = timeSlot;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
