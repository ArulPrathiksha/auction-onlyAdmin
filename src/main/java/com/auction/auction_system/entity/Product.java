package com.auction.auction_system.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;


@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private BigDecimal price; // Starting price for the product

    @ManyToOne
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private User seller; // Seller who posted the product

    private boolean sold; // Whether the product has been sold or not

    @ManyToOne
    @JoinColumn(name = "buyer_id", referencedColumnName = "id", nullable = true)
    private User buyer; // Buyer who won the product (null if unsold)

    private BigDecimal winningBidAmount; // Amount of the winning bid
    private LocalDateTime bidPlacedTime; // Time when the winning bid was placed (optional)

    @ManyToOne
    @JoinColumn(name = "auction_id", referencedColumnName = "id")
    @JsonIgnore
    private Auction auction; // Auction this product belongs to

    public Product() {
    }

    public Product(Long id, String name, String description, BigDecimal price, User seller, boolean sold, User buyer,
            BigDecimal winningBidAmount, LocalDateTime bidPlacedTime, Auction auction) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.seller = seller;
        this.sold = sold;
        this.buyer = buyer;
        this.winningBidAmount = winningBidAmount;
        this.bidPlacedTime = bidPlacedTime;
        this.auction = auction;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public BigDecimal getWinningBidAmount() {
        return winningBidAmount;
    }

    public void setWinningBidAmount(BigDecimal winningBidAmount) {
        this.winningBidAmount = winningBidAmount;
    }

    public LocalDateTime getBidPlacedTime() {
        return bidPlacedTime;
    }

    public void setBidPlacedTime(LocalDateTime bidPlacedTime) {
        this.bidPlacedTime = bidPlacedTime;
    }

    public Auction getAuction() {
        return auction;
    }

    public void setAuction(Auction auction) {
        this.auction = auction;
    }
}
