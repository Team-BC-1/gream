package bc1.gream.domain.order.service.command;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.helper.CouponCalculator;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderCommandService {

    private final OrderRepository orderRepository;

    /**
     * 구매입찰에 대한 쿠폰적용된 주문 발행 및 저장
     *
     * @param buy    구매입찰
     * @param seller 판매자
     * @param coupon 쿠폰
     * @return 새로운 주문
     */
    public Order saveOrderOfBuy(Buy buy, User seller, Coupon coupon) {
        Long finalPrice = CouponCalculator.calculateDiscount(coupon, buy.getPrice());
        Order order = Order.builder()
            .product(buy.getProduct())
            .buyer(buy.getUser())
            .seller(seller)
            .finalPrice(finalPrice)
            .expectedPrice(buy.getPrice())
            .build();
        return orderRepository.save(order);
    }

    public Order saveOrderOfBuyNotCoupon(Buy buy, User seller) {

        Long finalPrice = buy.getPrice();

        Order order = Order.builder()
            .product(buy.getProduct())
            .buyer(buy.getUser())
            .seller(seller)
            .finalPrice(finalPrice)
            .expectedPrice(buy.getPrice())
            .build();

        return orderRepository.save(order);
    }

    public Order saveOrderOfSell(Sell sell, User buyer, Coupon coupon) {
        Long finalPrice = CouponCalculator.calculateDiscount(coupon, sell.getPrice());

        Order order = Order.builder()
            .product(sell.getProduct())
            .buyer(buyer)
            .seller(sell.getUser())
            .finalPrice(finalPrice)
            .expectedPrice(sell.getPrice())
            .build();

        return orderRepository.save(order);
    }

    public Order saveOrderOfSellNotCoupon(Sell sell, User buyer) {

        Long finalPrice = sell.getPrice();

        Order order = Order.builder()
            .product(sell.getProduct())
            .buyer(buyer)
            .seller(sell.getUser())
            .finalPrice(finalPrice)
            .expectedPrice(sell.getPrice())
            .build();

        return orderRepository.save(order);
    }

    /**
     * 즉시판매 시, 쿠폰 상태에 따른 주문 체결
     *
     * @param buy    판매입찰
     * @param seller 판매자
     * @param coupon 쿠폰
     * @return 체결된 주문
     */
    public Order saveOrderOf(Buy buy, User seller, Coupon coupon) {
        if (coupon != null) {
            return saveOrderOfBuy(buy, seller, coupon);
        }
        return saveOrderOfBuyNotCoupon(buy, seller);
    }

    /**
     * 즉시구매 시, 쿠폰 상태에 따른 주문 체결
     *
     * @param sell   구매입찰
     * @param buyer  구매자
     * @param coupon 쿠폰
     * @return 체결된 주문
     */
    public Order saveOrderOf(Sell sell, User buyer, Coupon coupon) {
        if (coupon != null) {
            return saveOrderOfSell(sell, buyer, coupon);
        }
        return saveOrderOfSellNotCoupon(sell, buyer);
    }
}
