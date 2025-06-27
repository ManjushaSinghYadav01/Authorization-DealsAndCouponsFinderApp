package com.example.DealsService.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.DealsService.Exception.DealNotFound;
import com.example.DealsService.Model.Deal;
import com.example.DealsService.Service.DealService;


@RestController
@RequestMapping("/deals/admin")
public class AdminDealController {

	@Autowired
    private DealService service;


    @PostMapping("/createDeal")
    public ResponseEntity<Deal> createDeal(@RequestBody Deal deal) {
        Deal createdDeal = service.createDeal(deal);
        return new ResponseEntity<>(createdDeal, HttpStatus.CREATED);
    }


    @PostMapping("/createDeals")
    public ResponseEntity<List<Deal>> createDeals(@RequestBody List<Deal> deals) {
        List<Deal> createdDeals = service.createDeals(deals);
        return new ResponseEntity<>(createdDeals, HttpStatus.CREATED);
    }
    

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

    
    @PutMapping("/update/id/{id}")
    public ResponseEntity<Deal> updateDeal(@PathVariable Long id, @RequestBody Deal deal) {
        Deal updatedDeal = service.updateDeal(id, deal);
        return new ResponseEntity<>(updatedDeal, HttpStatus.OK);
    }

    
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteDeal(@PathVariable Long id) {
        service.deleteDeal(id);
        return new ResponseEntity<>("Deal deleted successfully.", HttpStatus.OK);
    }
}
