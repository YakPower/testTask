package com.example.testTask.Controllers;

import com.example.testTask.Entities.Price;
import com.example.testTask.Services.PriceService;
import com.example.testTask.Services.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;
    @Autowired
    private ProductPriceService productPriceService;

    @PostMapping
    public ResponseEntity<Price> createPrice(@RequestBody Price price) {
        return new ResponseEntity<>(priceService.savePrice(price), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Price>> getPrices() {
        return new ResponseEntity<>(priceService.findAllPrices(), HttpStatus.OK);
    }

    @GetMapping("/availableProductPrices")
    public ResponseEntity<List<Object[]>> getProductTypes(@RequestParam Long supplierId,@RequestParam LocalDate date) {
        List<Object[]> productTypes = productPriceService.getProductWithPrice(supplierId,date);
        return ResponseEntity.ok(productTypes);
    }

    @GetMapping("/validate")
    public ResponseEntity<Boolean> validatePricePeriod(
            @RequestParam Long supplierId,
            @RequestParam Long productId,
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        boolean isValid = priceService.validatePricePeriod(supplierId, productId, startDate, endDate);
        return ResponseEntity.ok(isValid);
    }

}

