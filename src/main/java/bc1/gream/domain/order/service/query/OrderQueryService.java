package bc1.gream.domain.order.service.query;

import bc1.gream.domain.buy.dto.response.BuyCheckOrderResponseDto;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderQueryService {

    private final OrderRepository orderRepository;

    public List<Order> findAllTradesOf(Product product) {
        return orderRepository.findAllByProductOrderByCreatedAtDesc(product);
    }

    public List<Order> findAllOrderBySeller(User user) {
        return orderRepository.findAllBySellerOrderByCreatedAtDesc(user);
    }

    public List<BuyCheckOrderResponseDto> findAllBoughtOrder(User user) {
        List<Order> orders = orderRepository.findAllBoughtBy(user);

        return orders.stream().map(OrderMapper.INSTANCE::toBuyCheckOrderResponseDto).toList();
    }
}
