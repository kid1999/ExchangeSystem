package io.kid1999.esystem.config;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
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

    @Value("${es.port}")
    private int ES_PORT;



    private static final int CONNECT_TIMEOUT = 5000 * 1000;
    private static final int SOCKET_TIMEOUT = 5000 * 1000;


    @Bean
    public RestHighLevelClient restHighLevelClient() {

//        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
//                .connectedTo(ES_HOST)
//                .build();
//        return RestClients.create(clientConfiguration).rest();

        RestHighLevelClient highLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost(ES_HOST, ES_PORT, "http"))
                        .setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback() {
                            // 该方法接收一个RequestConfig.Builder对象，对该对象进行修改后然后返回。
                            @Override
                            public RequestConfig.Builder customizeRequestConfig(
                                    RequestConfig.Builder requestConfigBuilder) {
                                return requestConfigBuilder.setConnectTimeout(CONNECT_TIMEOUT) // 连接超时（默认为1秒）
                                        .setSocketTimeout(SOCKET_TIMEOUT);// 套接字超时（默认为30秒）//更改客户端的超时限制默认30秒现在改为100*1000分钟
                            }
                        }));// 调整最大重试超时时间（默认为30秒）.setMaxRetryTimeoutMillis(60000);

        return highLevelClient;
    }
}