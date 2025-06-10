package be.dsystem.thepriceisright.services;

import be.dsystem.thepriceisright.dtos.ShopEntityDto;
import be.dsystem.thepriceisright.mappers.ShopEntityMapper;
import be.dsystem.thepriceisright.repositories.ShopEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ShopService {
    @Autowired
    private ShopEntityRepository shopRepository;
    @Autowired
    private ShopEntityMapper shopEntityMapper;

    public Page<ShopEntityDto> getShops(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return shopRepository.findAll(pageRequest)
                .map(shopEntityMapper::toDto);
    }

    public ShopEntityDto getShopById(Integer id) {
        return shopRepository.findById(id)
                .map(shopEntityMapper::toDto)
                .orElse(null);
    }

    public ShopEntityDto createShop(ShopEntityDto shopEntityDto) {
        return shopEntityMapper.toDto(shopRepository.save(shopEntityMapper.toEntity(shopEntityDto)));
    }

    public ShopEntityDto updateShop(Integer id, ShopEntityDto shopEntityDto) {
        return shopRepository.findById(id)
                .map(existingShop -> {
                    shopEntityMapper.partialUpdate(shopEntityDto, existingShop);
                    return shopEntityMapper.toDto(shopRepository.save(existingShop));
                })
                .orElse(null);
    }

    public void deleteShop(Integer id) {
        shopRepository.deleteById(id);
    }
}
