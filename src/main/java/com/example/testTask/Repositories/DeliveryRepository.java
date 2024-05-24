package com.example.testTask.Repositories;

import com.example.testTask.Entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;


import java.time.LocalDate;
import java.util.List;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    List<Delivery> findByDateBetween(LocalDate startDate, LocalDate endDate);
    Boolean existsBySupplierIdAndDate(Long supplierID,LocalDate date);

    List<Delivery> findBySupplierId(Long supplierId);
}
