package be.dsystem.thepriceisright.services;

import be.dsystem.thepriceisright.dtos.PriceEntityDto;
import be.dsystem.thepriceisright.mappers.PriceEntityMapper;
import be.dsystem.thepriceisright.model.PriceEntity;
import be.dsystem.thepriceisright.repositories.PriceEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PriceService {
    @Autowired
    private PriceEntityRepository priceEntityRepository;

    @Autowired
    private PriceEntityMapper priceEntityMapper;

    //Add a price from a PriceEntityDto
    public void addPrice(be.dsystem.thepriceisright.dtos.PriceEntityDto priceEntityDto) {
        PriceEntity priceEntity = priceEntityMapper.toEntity(priceEntityDto);
        priceEntityRepository.save(priceEntity);
    }

    //get paged price
    public Page<PriceEntityDto> getPagedPrices(
            int page, int size) {
        Pageable pageable = PageRequest.of(page,size);

        return priceEntityRepository.findAll(pageable)
                .map(priceEntityMapper::toDto);
    }

    //get price by id
    public PriceEntityDto getPriceById(Integer id) {
        return priceEntityRepository.findById(id)
                .map(priceEntityMapper::toDto)
                .orElse(null);
    }

    //update price by id
    public PriceEntityDto updatePriceById(Integer id, PriceEntityDto priceEntityDto) {
        return priceEntityRepository.findById(id)
                .map(existingPrice -> {
                    priceEntityMapper.partialUpdate(priceEntityDto, existingPrice);
                    return priceEntityMapper.toDto(priceEntityRepository.save(existingPrice));
                })
                .orElse(null);
    }

    //delete price by id
    public void deletePriceById(Integer id) {
        priceEntityRepository.deleteById(id);
    }
}
