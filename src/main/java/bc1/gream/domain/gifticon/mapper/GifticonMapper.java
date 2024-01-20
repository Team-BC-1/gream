package bc1.gream.domain.gifticon.mapper;

import bc1.gream.domain.buy.dto.response.UserPurchaseHistoryResponseDto;
import bc1.gream.domain.gifticon.entity.Gifticon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN) // ReportingPolicy.IGNORE 사용도 고려
public interface GifticonMapper {

    GifticonMapper INSTANCE = Mappers.getMapper(GifticonMapper.class);

    @Mapping(source = "id", target = "gifticonId")
    @Mapping(expression = "java(gifticon.getOrder().getId())", target = "orderId")
    @Mapping(expression = "java(gifticon.getOrder().getCreatedAt())", target = "orderCreatedAt")
    @Mapping(expression = "java(gifticon.getOrder().getExpectedPrice())", target = "orderExpectedPrice")
    @Mapping(expression = "java(gifticon.getOrder().getFinalPrice())", target = "orderFinalPrice")
    @Mapping(expression = "java(gifticon.getOrder().getProduct().getId())", target = "productId")
    @Mapping(expression = "java(gifticon.getOrder().getProduct().getName())", target = "productBrand")
    @Mapping(expression = "java(gifticon.getOrder().getProduct().getBrand())", target = "productName")
    UserPurchaseHistoryResponseDto toBuyCheckOrderResponseDto(Gifticon gifticon);
}
