package com.auction.auction_system.service;

import com.auction.auction_system.entity.Participant;
import com.auction.auction_system.entity.User;
import com.auction.auction_system.repository.ParticipantRepository;
import com.auction.auction_system.repository.UserRepository;
import com.auction.auction_system.repository.AuctionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final AuctionRepository auctionRepository;
    private final UserRepository userRepository;

    public ParticipantService(ParticipantRepository participantRepository, AuctionRepository auctionRepository, UserRepository userRepository) {
        this.participantRepository = participantRepository;
        this.auctionRepository = auctionRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public String registerForAuction(Long auctionId, Long userId, String proof) {
        // Ensure the auction exists
        if (!auctionRepository.existsById(auctionId)) {
            return "Auction not found";
        }

        // Ensure the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Ensure the user is not a seller or admin
        if ("SELLER".equalsIgnoreCase(user.getRole())) {
            return "Sellers cannot participate in the auction.";
        }

        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            return "Admins cannot participate in the auction.";
        }

        // Check if the user is already registered for this auction
        if (participantRepository.findByAuctionIdAndUserId(auctionId, userId).isPresent()) {
            return "User already registered for this auction.";
        }

        // Register the user as a participant
        Participant participant = new Participant();
        participant.setAuctionId(auctionId);
        participant.setUserId(userId);
        participant.setProof(proof);
        participantRepository.save(participant);

        return "Successfully registered for the auction.";
    }
}

