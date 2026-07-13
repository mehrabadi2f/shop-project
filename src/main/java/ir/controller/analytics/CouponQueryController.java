package ir.controller.analytics;

import ir.model.analytics.IssuedCoupon;
import ir.repository.analytics.IssuedCouponRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
public class CouponQueryController {

    private final IssuedCouponRepository issuedCouponRepository;

    public CouponQueryController(IssuedCouponRepository issuedCouponRepository) {
        this.issuedCouponRepository = issuedCouponRepository;
    }

    @GetMapping("/user/{userId}")
    public List<IssuedCoupon> getUserCoupons(@PathVariable Long userId) {
        return issuedCouponRepository.findByUserIdAndActiveTrue(userId);
    }
}
