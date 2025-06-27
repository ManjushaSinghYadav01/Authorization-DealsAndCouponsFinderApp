package com.CouponsService;

import com.CouponsService.Exception.CouponNotFoundException;
import com.CouponsService.Model.Coupon;
import com.CouponsService.Repository.CouponRepository;
import com.CouponsService.Service.CouponService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponServiceTest {

    @Mock
    private CouponRepository couponRepository;

    @InjectMocks
    private CouponService couponService;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        coupon = new Coupon(
                1L,
                "SAVE10",
                true,
                10.0,
                "Electronics",
                500.0,
                2000.0,
                LocalDate.now().plusDays(10)
        );
    }

    @Test
    void createCoupon_shouldSaveCoupon() {
        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);
        Coupon saved = couponService.createCoupon(coupon);
        assertEquals("SAVE10", saved.getCode());
        verify(couponRepository, times(1)).save(coupon);
    }

    @Test
    void getCouponById_found() {
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        Optional<Coupon> found = couponService.getCouponById(1L);
        assertTrue(found.isPresent());
        assertEquals("SAVE10", found.get().getCode());
    }

    @Test
    void getCouponById_notFound() {
        when(couponRepository.findById(2L)).thenReturn(Optional.empty());
        Optional<Coupon> found = couponService.getCouponById(2L);
        assertTrue(found.isEmpty());
    }

    @Test
    void updateCoupon_found() {
        Coupon updated = new Coupon(1L, "SAVE15", false, 15.0, "Electronics", 400.0, 1500.0, LocalDate.now().plusDays(20));
        when(couponRepository.findById(1L)).thenReturn(Optional.of(coupon));
        when(couponRepository.save(any(Coupon.class))).thenReturn(updated);

        Coupon result = couponService.updateCoupon(1L, updated);

        assertEquals("SAVE15", result.getCode());
        assertEquals(15.0, result.getDiscountPercent());
        verify(couponRepository).save(any(Coupon.class));
    }

    @Test
    void updateCoupon_notFound() {
        Coupon updated = new Coupon();
        when(couponRepository.findById(100L)).thenReturn(Optional.empty());
        assertThrows(CouponNotFoundException.class, () -> couponService.updateCoupon(100L, updated));
    }

    @Test
    void deleteCoupon_found() {
        when(couponRepository.existsById(1L)).thenReturn(true);
        doNothing().when(couponRepository).deleteById(1L);

        boolean deleted = couponService.deleteCoupon(1L);
        assertTrue(deleted);
        verify(couponRepository).deleteById(1L);
    }

    @Test
    void deleteCoupon_notFound() {
        when(couponRepository.existsById(100L)).thenReturn(false);
        assertThrows(CouponNotFoundException.class, () -> couponService.deleteCoupon(100L));
    }
}