package be.dsystem.thepriceisright.repositories;

import be.dsystem.thepriceisright.model.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleEntityRepository extends JpaRepository<RoleEntity, Integer> {
}