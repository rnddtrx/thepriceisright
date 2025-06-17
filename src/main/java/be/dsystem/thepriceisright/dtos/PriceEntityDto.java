package be.dsystem.thepriceisright.dtos;

import lombok.Value;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for {@link be.dsystem.thepriceisright.model.PriceEntity}
 */
@Value
public class PriceEntityDto implements Serializable {
    Integer id;
    BigDecimal price;
    Boolean promo;
    LocalDate start;
    LocalDate end;
    PriceProductEntityDto productEntity;
    PriceShopEntityDto shopEntity;
}