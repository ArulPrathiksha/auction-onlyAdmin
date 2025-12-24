
package com.auction.auction_system.repository;

import com.auction.auction_system.entity.Auction;
import com.auction.auction_system.entity.AuctionStatus;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/*@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction> findByStatus(AuctionStatus status);


    @Query("select case when count(a)>0 then true else false end from Auction a where a.timeSlot.startTime < :end and a.timeSlot.endTime > :start")
    boolean existsOverlapping(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}


*/
@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {

    List<Auction> findByStatus(AuctionStatus status);

    @Query("select case when count(a)>0 then true else false end from Auction a where a.timeSlot.startTime < :end and a.timeSlot.endTime > :start")
    boolean existsOverlapping(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}




