package bc1.gream.domain.product.service;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.service.query.ProductQueryService;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.service.SellService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SellOrderQueryService {

    private final ProductQueryService productQueryService;
    private final SellService sellService;


    /**
     * 상품아이디값을 통한 상품 조회 이후, 해당 상품의 판매입찰 내역에 대한 페이징 데이터 반환
     *
     * @param productId 이모티콘 상품 id값
     * @param pageable  페이징 요청 입력
     * @return 해당 상품에 대한 판매입찰 내역
     * @author 임지훈
     */
    public Page<Sell> findAllSellBidsOf(Long productId, Pageable pageable) {
        Product product = productQueryService.findBy(productId);
        return sellService.findAllSellBidsOf(product, pageable);
    }
}
