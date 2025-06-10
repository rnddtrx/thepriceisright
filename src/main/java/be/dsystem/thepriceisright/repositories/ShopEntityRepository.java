package be.dsystem.thepriceisright.repositories;

import be.dsystem.thepriceisright.model.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopEntityRepository extends JpaRepository<ShopEntity, Integer> {
}
