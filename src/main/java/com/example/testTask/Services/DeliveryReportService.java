package com.example.testTask.Services;

import com.example.testTask.Entities.DTO.ReportDTO;
import com.example.testTask.Entities.Delivery;
import com.example.testTask.Entities.DeliveryItem;
import com.example.testTask.Repositories.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeliveryReportService {

    @Autowired
    private DeliveryRepository deliveryRepository;

    public List<ReportDTO> getDeliveryReport(LocalDate startDate, LocalDate endDate) {
        List<Delivery> deliveries = deliveryRepository.findByDateBetween(startDate, endDate);
        Map<String, ReportDTO> supplierReports = new HashMap<>();

        for (Delivery delivery : deliveries) {
            String supplierName = delivery.getSupplier().getName();
            supplierReports.computeIfAbsent(supplierName, k -> new ReportDTO(supplierName));

            ReportDTO report = supplierReports.get(supplierName);
            for (DeliveryItem item : delivery.getItems()) {
                String productName = item.getProduct().getName();
                double cost = item.getCost();
                double quantity = item.getQuantity();

                report.addProductCost(productName, cost);
                report.addProductQuantity(productName, quantity);
            }
        }

        return new ArrayList<>(supplierReports.values());
    }
}