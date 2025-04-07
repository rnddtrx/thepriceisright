package be.dsystem.thepriceisright.services;

import be.dsystem.thepriceisright.dtos.ProductEntityDto;
import be.dsystem.thepriceisright.mappers.ProductEntityMapper;
import be.dsystem.thepriceisright.model.ProductEntity;
import be.dsystem.thepriceisright.repositories.ProductEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductEntityRepository productEntityRepository;
    @Autowired
    private ProductEntityMapper productEntityMapper;

    public List<ProductEntity> findAll() {
        return productEntityRepository.findAll();
    }

    // find by name method (findByNameContaining)
    public Page<ProductEntity> findByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        return productEntityRepository.findByNameContaining(name, pageable);
    }

    public Page<ProductEntityDto> finAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("name").ascending());
        // convert my page of entity into page of dto
        Page<ProductEntity> productEntityPage = productEntityRepository.findAll(pageable);
        return productEntityMapper.toDtoPage(productEntityPage);
    }


    public ProductEntity save(ProductEntity productEntity) {
        return productEntityRepository.save(productEntity);
    }

    //saveAll method
    public List<ProductEntity> saveAll(List<ProductEntity> productEntities) {
        return productEntityRepository.saveAll(productEntities);
    }
}
