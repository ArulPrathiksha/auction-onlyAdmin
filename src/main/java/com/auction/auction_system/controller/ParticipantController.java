package com.auction.auction_system.controller;

import com.auction.auction_system.service.ParticipantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auctions")
public class ParticipantController {

    private final ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/{id}/register")
    public ResponseEntity<?> register(@PathVariable Long id, @RequestParam Long userId, @RequestParam(required = false) String proof) {
        // Call the service to register the user
        String responseMessage = participantService.registerForAuction(id, userId, proof);
        
        // Return the response based on the service output
        if (responseMessage.equals("Successfully registered for the auction.")) {
            return ResponseEntity.ok(responseMessage);
        }
        
        return ResponseEntity.status(400).body(responseMessage); // Bad request if failed
    }
}
