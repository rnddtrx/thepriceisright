package be.dsystem.thepriceisright.repositories;

import be.dsystem.thepriceisright.model.PriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceEntityRepository extends JpaRepository<PriceEntity, Integer> {
}