package com.example.testTask.Services;

import com.example.testTask.Entities.Price;
import com.example.testTask.Repositories.PriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class PriceService {

    @Autowired
    private PriceRepository priceRepository;

    public Price savePrice(Price price) {
        return priceRepository.save(price);
    }
    public List<Price> findAllPrices() {
        return priceRepository.findAll();
    }
    public List<Price> getPricesForSupplierAndProduct(Long supplierId, Long productId, LocalDate date) {
        return priceRepository.findBySupplierIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(supplierId, productId, date, date);
    }
    public boolean validatePricePeriod(Long supplierId, Long productId, LocalDate startDate, LocalDate endDate) {
        List<Price> existingPrices = priceRepository.findBySupplierIdAndProductId(supplierId, productId);
        for (Price price : existingPrices) {
            LocalDate existingStartDate = price.getStartDate();
            LocalDate existingEndDate = price.getEndDate();

            if ((startDate.isBefore(existingEndDate) || startDate.isEqual(existingEndDate)) &&
                    (endDate.isAfter(existingStartDate) || endDate.isEqual(existingStartDate))) {
                return false;
            }
        }
        return true;
    }

    public double getProductPriceByDeliveryDate(Long productId, LocalDate deliveryDate) {
        List<Price> prices = priceRepository.findByProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(productId, deliveryDate,deliveryDate);
        if (!prices.isEmpty()) {
            return prices.get(0).getPrice();
        }
        return 0;
    }

}
