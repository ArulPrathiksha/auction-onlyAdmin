package com.auction.auction_system.repository;

import com.auction.auction_system.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByAuctionId(Long auctionId);
    List<Product> findBySellerId(Long sellerId);
    List<Product> findByBuyerId(Long buyerId);
}
