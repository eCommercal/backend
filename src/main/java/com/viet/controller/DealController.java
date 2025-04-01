package com.viet.controller;

import com.viet.model.Deal;
import com.viet.response.ApiResponse;
import com.viet.service.Impl.DealService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/deals")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DealController {

    DealService dealService;

    @PostMapping
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) throws Exception {
        Deal createDeal = dealService.createDeal(deal);
        return new ResponseEntity<>(createDeal, HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal deal) throws Exception {
        Deal updateDeal = dealService.updateDeal(deal, id);

        return new ResponseEntity<>(updateDeal, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDeal(@PathVariable Long id) throws Exception {

        dealService.deleteDeal(id);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Deal deleted successfully");
        
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }
}
