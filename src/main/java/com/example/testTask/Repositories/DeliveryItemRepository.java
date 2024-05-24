package com.example.testTask.Repositories;

import com.example.testTask.Entities.DeliveryItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryItemRepository extends JpaRepository<DeliveryItem,Long> {
    List<DeliveryItem> findByDeliveryId(Long deliveryId);
}
