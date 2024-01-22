package bc1.gream.domain.payment.toss.dto.response;

import bc1.gream.domain.payment.toss.entity.OrderName;
import bc1.gream.domain.payment.toss.entity.PayType;
import lombok.Builder;

/* 결제요청 시, 필요한 값 */
@Builder
public record TossPaymentInitialResponseDto(
    PayType payType,            // 결제방법
    Long amount,                // 결제금액
    Long orderId,               // 주문Id
    OrderName orderName,        // 주문명
    String userLoginId,         // 구매자 아이디
    String userNickname,        // 구매자 닉네임
    String paymentSuccessUrl,   // 주문 성공 시 콜백 주소
    String paymentFailUrl,      // 주문 실패 시 콜백 주소
    String paymentCreatedAt,    // 결제날짜
    Boolean paymentHasSuccess   // 결제 성공 여부
) {

}
