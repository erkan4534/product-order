package com.microservices.order.config;

import com.microservices.order.client.InventoryClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {


    /**
     * @FeignClient vs HttpServiceProxyFactory
     *
     * = Ortak Nokta =
     *
     * İkisi de interface tabanlı HTTP client oluşturur.
     *
     * Sen sadece interface yazarsın, runtime’da Spring bu interface’in implementasyonunu proxy olarak üretir.
     *
     * Manuel RestTemplate / WebClient kodu yazmana gerek kalmaz.
     *
     * = @FeignClient (Spring Cloud OpenFeign) =
     *
     * Spring Cloud bağımlılığı gerekir (spring-cloud-starter-openfeign).
     *
     * Annotation: @FeignClient
     *
     * Yalnızca blocking (senkron) çalışır.
     *
     * Yaygın, basit, Spring Cloud projelerinde sık kullanılır.
     *
     * Örnek:
     *
     * @FeignClient(name = "inventory", url = "${inventory.url}")
     * public interface InventoryClient {
     *     @GetMapping("/api/inventory")
     *     boolean isInStock(@RequestParam String skuCode,
     *                       @RequestParam Integer quantity);
     * }
     *
     * @FeignClient → Spring Cloud OpenFeign’in bir parçası.
     * OpenFeign, kökeninde Netflix Feign tabanlı.
     * Ama günümüzde Spring Cloud bağımsız olarak OpenFeign’i geliştirmeye devam ediyor (Netflix Feign projeyi bıraktı).
     *
     * = HttpServiceProxyFactory (Spring 6 / Boot 3) =
     *
     * Spring Framework 6 ile native geldi (ek bağımlılık yok).
     *
     * Annotation: @GetExchange, @PostExchange …
     *
     * Hem blocking (RestClient) hem reactive (WebClient) destekler.
     *
     * Daha modern ve Feign’in yerine kullanılabilir.
     *
     * Örnek:
     *
     * public interface InventoryClient {
     *     @GetExchange("/api/inventory")
     *     boolean isInStock(@RequestParam String skuCode,
     *                       @RequestParam Integer quantity);
     * }
     *
     *
     * | Özellik          | Feign Client (@FeignClient)            | HttpServiceProxyFactory         |
     * | ---------------- | -------------------------------------- | ------------------------------- |
     * | Geldiği Yer      | Spring Cloud (OpenFeign)               | Spring Framework 6 (native)     |
     * | Ek Bağımlılık    | Var (`spring-cloud-starter-openfeign`) | Yok                             |
     * | Annotation       | `@FeignClient`, `@GetMapping`          | `@GetExchange`, `@PostExchange` |
     * | Reactive Desteği | ❌ Yok                                  | ✅ Var (WebClient)               |
     * | Kullanım Alanı   | Spring Cloud projeleri                 | Modern Spring (Boot 3+)         |
     *
     *
     *
     *
     *
     * */


    @Value("${inventory.url}")
    private String inventoryServiceUrl;

    @Bean
    public InventoryClient inventoryClient() {
        RestClient restClient = RestClient.builder()
                .baseUrl(inventoryServiceUrl)
                .build();
        var restClientAdapter = RestClientAdapter.create(restClient);
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
        return httpServiceProxyFactory.createClient(InventoryClient.class);
    }
}
