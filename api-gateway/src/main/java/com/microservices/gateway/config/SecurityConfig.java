package com.microservices.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /*
     * authorize.anyRequest().authenticated()
     * "Gelen her HTTP isteği mutlaka kimlik doğrulanmış (authenticated) olmalı" demek.
     * Yani hiçbir endpoint public değil, hepsi token ister.
     *
     * .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
     * "Bu uygulama(api-gateway) bir OAuth2 Resource Server olacak ve JWT token doğrulaması yapacak" demek.
     *
     * oauth2ResourceServer(...) → Spring’e, "benim isteklerim Bearer Token ile gelecek" diyor.
     *
     * jwt(Customizer.withDefaults()) → gelen token’ın bir JWT olduğunu ve
     * Spring’in default ayarlarla (issuer-uri veya jwk-set-uri’den) doğrulamasını söylüyor.
     *
     * Spring Security’nin 6.x sürümüyle birlikte artık WebSecurityConfigurerAdapter kullanılmıyor.
     * Bunun yerine SecurityFilterChain bean tanımlıyorsun.
     * Yani: Bu metod → uygulamanın güvenlik kurallarını belirleyen bean.
     *
     * Özetle Bu config şunu yapıyor:
     * Tüm endpointler korumalı → herkesin JWT access token’ı olmalı.
     * Access token geldiğinde Spring, Keycloak’ın public key’ini kullanarak token’ı doğruluyor.
     * Doğru ise request içeri alınıyor, yanlış ise 401 Unauthorized dönüyor.
     *
     */

   private final String[] freeResourceUrls = {
           "/swagger-ui.html",
           "/swagger-ui/**",
           "/v3/api-docs/**",
           "/swagger-resources/**",
           "/api-docs/**",
           "/aggregate/**",
   };

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
       return  httpSecurity.authorizeHttpRequests(authorize ->authorize
                       .requestMatchers(freeResourceUrls)
                       .permitAll() // Bu URL'ler public, kimlik doğrulama istemiyor
                       .anyRequest().authenticated())
               .oauth2ResourceServer(oauth2-> oauth2.jwt(Customizer.withDefaults()))
               .build();
   }


}
