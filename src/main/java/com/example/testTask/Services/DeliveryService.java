package com.example.testTask.Services;

import com.example.testTask.Entities.Delivery;
import com.example.testTask.Repositories.DeliveryItemRepository;
import com.example.testTask.Repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private DeliveryItemRepository deliveryItemRepository;


    public Delivery saveDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
    }


    public List<Delivery> getAllDeliveries() {
        return deliveryRepository.findAll();
    }
    public List<Delivery> getDeliveryDates(Long supplierId) {

        return deliveryRepository.findBySupplierId(supplierId); //deliveryRepository.findDeliveryDatesBySupplierId(supplierId);
    }


    public List<Delivery> getDeliveriesForPeriod(LocalDate startDate, LocalDate endDate) {
        return deliveryRepository.findByDateBetween(startDate, endDate);
    }

    public boolean isDeliveryExistsForSupplierOnDate(Long supplierId, LocalDate date) {
        return deliveryRepository.existsBySupplierIdAndDate(supplierId, date);
    }

}

