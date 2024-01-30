package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCutom {

    List<Order> findAllByProductOrderByCreatedAtDesc(Product product);

    List<Order> findAllBySellerOrderByCreatedAtDesc(User seller);
}
