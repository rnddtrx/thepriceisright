package be.dsystem.thepriceisright.dtos;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link be.dsystem.thepriceisright.model.ShopEntity}
 */
@Value
public class ShopEntityDto implements Serializable {
    Integer shopId;
    String name;
    String address;
    String city;
    String postalCode;
    String websiteUrl;
}