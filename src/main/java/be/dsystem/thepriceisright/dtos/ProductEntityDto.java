package be.dsystem.thepriceisright.dtos;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link be.dsystem.thepriceisright.model.ProductEntity}
 */
@Value
public class ProductEntityDto implements Serializable {
    Long productId;
    String brand;
    String name;
    String description;
    String eanCode;
    LocalDateTime lastUpdated;
    String nudgerUrl;
}