package com.pablodiazjorge.crud.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {

    @Value("crudAdvanced")
    private String cloudName;

    @Value("153118342271156")
    private String apiKey;

    @Value("f5qviYfhDEMLGPCLlf2-X5R6ZbA")
    private String apiSecret;

    /**
     * Configures and initializes the Cloudinary bean.
     *
     * @return Cloudinary - The configured Cloudinary instance.
     */
    @Bean
    public Cloudinary cloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("cloud_name", cloudName);
        config.put("api_key", apiKey);
        config.put("api_secret", apiSecret);
        return new Cloudinary(config);
    }

}
