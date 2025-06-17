package be.dsystem.thepriceisright.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "shop_entity")
public class ShopEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer shopId;

    private String name;

    private String address;

    private String city;

    private String postalCode;

    private String websiteUrl;

    @OneToMany(mappedBy = "shopEntity", orphanRemoval = true)
    private Set<PriceEntity> priceEntities = new LinkedHashSet<>();

}