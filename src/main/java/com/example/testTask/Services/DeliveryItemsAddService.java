package com.example.testTask.Services;

import com.example.testTask.Entities.DTO.DeliveryItemDTO;
import com.example.testTask.Entities.Delivery;
import com.example.testTask.Entities.DeliveryItem;
import com.example.testTask.Entities.Product;
import com.example.testTask.Repositories.DeliveryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliveryItemsAddService {

    @Autowired
    DeliveryRepository deliveryRepository;

    @Autowired
    ProductService productService;
    @Autowired
    PriceService priceService;

    @Transactional
    public void addDeliveryItems(Long deliveryId, List<DeliveryItemDTO> itemDTOs) throws Exception {
        Delivery delivery = deliveryRepository.findById(deliveryId)
                .orElseThrow(() -> new Exception("Поставка не найдена id = " + deliveryId));

        for (DeliveryItemDTO itemDTO : itemDTOs) {
            Product product = productService.getProductById(itemDTO.getProductId());
            if (product == null) {
                throw new Exception("Продукт не найден id = " + itemDTO.getProductId());
            }

            boolean itemExists = delivery.getItems().stream()
                    .anyMatch(item -> item.getProduct().getId().equals(itemDTO.getProductId()));
            if (itemExists) {
                DeliveryItem existingItem = delivery.getItems().stream()
                        .filter(item -> item.getProduct().getId().equals(itemDTO.getProductId()))
                        .findFirst().orElse(null);
                if (existingItem != null) {
                    existingItem.setQuantity(existingItem.getQuantity() + itemDTO.getQuantity());
                    double price = priceService.getProductPriceByDeliveryDate(product.getId(), delivery.getDate());
                    existingItem.setCost(existingItem.getCost() + (itemDTO.getQuantity() * price));
                }
            } else {

                DeliveryItem newItem = new DeliveryItem();
                newItem.setProduct(product);
                newItem.setQuantity(itemDTO.getQuantity());
                newItem.setDelivery(delivery);
                double price = priceService.getProductPriceByDeliveryDate(product.getId(), delivery.getDate());
                newItem.setCost(itemDTO.getQuantity() * price);
                delivery.getItems().add(newItem);
            }
        }
        deliveryRepository.save(delivery);
    }

}
