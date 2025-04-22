package be.dsystem.thepriceisright.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "product_entity")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Column(name = "brand")
    private String brand;

    @Column(name = "nrame", nullable = false, unique = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "ean_code", unique = true)
    private String eanCode;

    @Column(name = "last_update", columnDefinition = "DATETIME2")
    private LocalDateTime lastUpdated;

    @Column(name = "nudger_url")
    private String nudgerUrl;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "is_disabled")
    private Boolean isDisabled;



}