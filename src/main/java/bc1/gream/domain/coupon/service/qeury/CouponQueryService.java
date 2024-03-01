package bc1.gream.domain.coupon.service.qeury;

import static bc1.gream.global.common.ResultCase.COUPON_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.repository.CouponRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.global.security.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CouponQueryService {

    private final CouponRepository couponRepository;

    public Coupon checkCoupon(Long couponId, User buyer, CouponStatus status) {

        Coupon coupon = findCouponById(couponId, buyer);

        if (!isCheckCouponStatus(coupon, status)) {
            throw new GlobalException(ResultCase.COUPON_STATUS_CHANGE_FAIL);
        }

        return coupon;
    }

    private boolean isCheckCouponStatus(Coupon coupon, CouponStatus status) {
        return coupon.getStatus().equals(status);
    }

    public List<Coupon> availableCouponList(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return couponRepository.availableCoupon(user);
    }

    public List<Coupon> unavailableCouponList(UserDetailsImpl userDetails) {
        User user = userDetails.getUser();
        return couponRepository.unavailable(user);
    }

    public Coupon findCouponById(Long couponId, User user) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(
            () -> new GlobalException(COUPON_NOT_FOUND)
        );

        if (!isMatchCouponUser(user, coupon)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        return coupon;
    }

    public boolean isMatchCouponUser(User user, Coupon coupon) {
        return coupon.getUser().getLoginId().equals(user.getLoginId());
    }

    /**
     * 쿠폰아이디와 쿠폰주인에 따라 쿠폰조회 이후 쿠폰상태 변경
     *
     * @param couponId 쿠폰아이디
     * @param user     쿠폰주인
     * @return 쿠폰
     */
    public Coupon getCouponFrom(Long couponId, User user) {
        if (couponId != null) {
            Coupon coupon = checkCoupon(couponId, user, CouponStatus.AVAILABLE);
            coupon.changeStatus(CouponStatus.ALREADY_USED);
            return coupon;
        }
        return null;
    }
}
