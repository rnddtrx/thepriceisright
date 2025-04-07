package be.dsystem.thepriceisright.repositories;

import be.dsystem.thepriceisright.model.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {

    Page<ProductEntity> findByNameContaining(String name, Pageable pageable);


}