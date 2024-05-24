package com.example.testTask.Controllers;

import com.example.testTask.Entities.DTO.DeliveryItemDTO;
import com.example.testTask.Entities.DTO.ReportDTO;
import com.example.testTask.Entities.Delivery;
import com.example.testTask.Services.DeliveryItemsAddService;
import com.example.testTask.Services.DeliveryReportService;
import com.example.testTask.Services.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/deliveries")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;
    @Autowired
    private DeliveryItemsAddService deliveryItemsAddService;
    @Autowired
    private DeliveryReportService deliveryReportService;

    @PostMapping
    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
        Delivery savedDelivery = deliveryService.saveDelivery(delivery);
        return new ResponseEntity<>(savedDelivery, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Delivery>> getAllDeliveries(){
        List<Delivery> allDeliveries = deliveryService.getAllDeliveries();
        return new ResponseEntity<>(allDeliveries, HttpStatus.OK);
    }
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validatePricePeriod(
            @RequestParam Long supplierId,
            @RequestParam LocalDate date
    ) {
        boolean isExist = deliveryService.isDeliveryExistsForSupplierOnDate(supplierId, date);
        return ResponseEntity.ok(!isExist);
    }

    @GetMapping("/dates")
    public ResponseEntity<List<Delivery>> getDeliveryDates(@RequestParam Long supplierId) {
        List<Delivery> deliveryDates = deliveryService.getDeliveryDates(supplierId);
        return ResponseEntity.ok(deliveryDates);
    }

    @PostMapping("/{deliveryId}/items")
    public ResponseEntity<String> addDeliveryItems(@PathVariable Long deliveryId, @RequestBody List<DeliveryItemDTO> itemDTOs) {
        try {
            deliveryItemsAddService.addDeliveryItems(deliveryId, itemDTOs);
            return ResponseEntity.ok("Продукты добавлены");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Не получилось добавить продукты: " + e.getMessage());
        }
    }


    @GetMapping("/report")
    public ResponseEntity<List<ReportDTO>> getDeliveryReport(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        List<ReportDTO> reports = deliveryReportService.getDeliveryReport(startDate, endDate);
        return new ResponseEntity<>(reports, HttpStatus.OK);

    }
}

