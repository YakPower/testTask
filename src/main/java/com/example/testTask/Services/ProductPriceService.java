package com.example.testTask.Services;

import com.example.testTask.Repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductPriceService {

    @Autowired
    private PriceRepository priceRepository;

   public List<Object[]> getProductWithPrice(Long supplierId, LocalDate date){
        return priceRepository.findProductPricesBySupplierIdAndDate(supplierId, date);
    }
}
