package bc1.gream.domain.sell.dto.request;

import lombok.Builder;

@Builder // 테스트용
public record SellNowRequestDto(
    Long price,
    String paymentType,
    String gifticonUrl
) {

}
