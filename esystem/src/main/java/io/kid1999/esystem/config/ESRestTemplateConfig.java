package io.kid1999.esystem.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

/**
 * @author kid1999
 * @create 2021-01-27 16:16
 * @description ES 客服端配置
 **/
@Configuration
public class ESRestTemplateConfig {

    @Value("${es.host}")
    private String ES_HOST;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(ES_HOST)
                .build();
        return RestClients.create(clientConfiguration).rest();
    }
}