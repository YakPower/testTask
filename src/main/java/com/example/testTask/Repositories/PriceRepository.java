package com.example.testTask.Repositories;

import com.example.testTask.Entities.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface PriceRepository extends JpaRepository<Price,Long> {
    List<Price> findBySupplierIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long supplierId, Long productId, LocalDate startDate, LocalDate endDate);

    List<Price> findBySupplierIdAndProductId(Long supplierId, Long productId);

    List<Price> findByProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(Long productId,LocalDate deliveryDate,LocalDate deliveryDate2);

    @Query("SELECT p.product.id, p.product.name, p.price FROM Price p WHERE p.supplier.id = :supplierId AND :date BETWEEN p.startDate AND p.endDate")
    List<Object[]> findProductPricesBySupplierIdAndDate(Long supplierId, LocalDate date);
}
