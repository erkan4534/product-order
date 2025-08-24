package com.microservices.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

/**
 *
 *
 * @FeignClient(value = "inventory", url = "${inventory.url}")
 * public interface InventoryClient {
 *     @GetMapping("/api/inventory")
 *     boolean isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);
 * }
 *
 *
 *
 *
 * */

public interface InventoryClient {
    @GetExchange("/api/inventory")
    boolean isInStock(@RequestParam("skuCode") String skuCode, @RequestParam("quantity") Integer quantity);
}

