package com.CouponsService.Controller;

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

import com.CouponsService.Exception.CouponNotFoundException;
import com.CouponsService.Model.Coupon;
import com.CouponsService.Service.CouponService;

@RestController
@RequestMapping("/coupons/admin")
public class AdminCouponController {

    @Autowired
    private CouponService couponService;

    
    @PostMapping("/createCoupon")
    public ResponseEntity<Coupon> createCoupon(@RequestBody Coupon coupon) {
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return new ResponseEntity<>(createdCoupon, HttpStatus.CREATED);
    }

    
    @PostMapping("/createCoupons")
    public ResponseEntity<List<Coupon>> createCoupons(@RequestBody List<Coupon> coupons) {
        List<Coupon> createdCoupons = couponService.createCoupons(coupons);
        return new ResponseEntity<>(createdCoupons, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Coupon>> getAllCoupons() {
        List<Coupon> coupons = couponService.getAllCoupons();
        return new ResponseEntity<>(coupons, HttpStatus.OK);
    }

    
    @GetMapping("/id/{id}")
    public ResponseEntity<Coupon> getCouponById(@PathVariable Long id) {
        Coupon coupon = couponService.getCouponById(id).orElseThrow(()->new CouponNotFoundException("Coupon not found with id:"+id));
        return  new ResponseEntity<>(coupon, HttpStatus.OK);
    }

    
    @GetMapping("/code/{code}")
    public ResponseEntity<Coupon> getCouponByCode(@PathVariable String code) {
    	 Coupon coupon = couponService.getCouponByCode(code).orElseThrow(() -> new CouponNotFoundException("Coupon not found with code: " + code));
         return new ResponseEntity<>(coupon, HttpStatus.OK);
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Coupon>> getCouponByCategory(@PathVariable String category) {
    	 List<Coupon> coupon = couponService.getCouponByCategory(category).orElseThrow(() -> new CouponNotFoundException("Coupon not found with code: " + category));
         return new ResponseEntity<>(coupon, HttpStatus.OK);
    }


    @PutMapping("/update/id/{id}")
    public ResponseEntity<Coupon> updateCoupon(@PathVariable Long id, @RequestBody Coupon coupon) {
        Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
        return  new ResponseEntity<>(updatedCoupon, HttpStatus.OK);
    }

    
    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<String> deleteCoupon(@PathVariable Long id) {
        couponService.deleteCoupon(id);
        return new ResponseEntity<>("Coupon deleted successfully.", HttpStatus.OK);
        }
    

}
