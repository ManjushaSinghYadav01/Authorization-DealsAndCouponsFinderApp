package com.CouponsService.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.CouponsService.Exception.CouponNotFoundException;
import com.CouponsService.Model.Coupon;
import com.CouponsService.Service.CouponService;

@RestController
@RequestMapping("/coupons/user")
public class UserCouponController {

    @Autowired
    private CouponService couponService;

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

    

 
}
