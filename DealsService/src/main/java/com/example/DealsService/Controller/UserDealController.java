package com.example.DealsService.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



import com.example.DealsService.Exception.DealNotFound;
import com.example.DealsService.Model.Deal;
import com.example.DealsService.Service.DealService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/deals/user")
public class UserDealController {

    @Autowired
    private DealService service;

    
    @GetMapping("/all")
    public ResponseEntity<List<Deal>> getAllDeals() {
        List<Deal> deals = service.getAllDeals();
        return new ResponseEntity<>(deals, HttpStatus.OK);
    }

   
    @GetMapping("/id/{id}")
    public ResponseEntity<Deal> getDealById(@PathVariable Long id) {
       Deal deal = service.getDealById(id).orElseThrow(()->new DealNotFound("Deal with ID " + id + " not found"));
        return new ResponseEntity<>(deal, HttpStatus.OK);
    }

   
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Deal>> getActiveDealsByCategory(@PathVariable String category) {
        List<Deal> deals = service.getActiveDealsByCategory(category);
        return new ResponseEntity<>(deals, HttpStatus.OK);
    }

   
}
