package com.itheima.sys.corebase.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.DefaultClientConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final Integer TIME_OUT = 30000;

    /*
     * @Bean public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
     * return new RestTemplate(factory); }
     *
     * @Bean public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
     * HttpComponentsClientHttpRequestFactory httpRequestFactory = new
     * HttpComponentsClientHttpRequestFactory();
     * httpRequestFactory.setConnectionRequestTimeout(30 * 1000);
     * httpRequestFactory.setConnectTimeout(15 * 1000);
     * httpRequestFactory.setReadTimeout(5 * 1000); return httpRequestFactory; }
     */

    @Bean
    public RestTemplate httpRestTemplate() {
        ClientHttpRequestFactory factory = httpRequestFactory();
        RestTemplate restTemplate = new RestTemplate(factory);
        // 可以添加消息转换
        // restTemplate.setMessageConverters(...);
        // 可以增加拦截器
        // restTemplate.setInterceptors(...);
        return restTemplate;
    }

    public ClientHttpRequestFactory httpRequestFactory() {
        return new HttpComponentsClientHttpRequestFactory(restTemplateConfigHttpClient());
    }

    public HttpClient restTemplateConfigHttpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory()).build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        // 设置整个连接池最大连接数 根据自己的场景决定
        // todo 后面调整从配置中心获取
        connectionManager.setMaxTotal(1000);
        // 路由是对maxTotal的细分
        // todo 后面调整从配置中心获取
        connectionManager.setDefaultMaxPerRoute(32);
        RequestConfig requestConfig = RequestConfig.custom()
                // 服务器返回数据(response)的时间，超过该时间抛出read timeout
                // todo 后面调整从配置中心获取
                .setSocketTimeout(TIME_OUT)
                // 连接上服务器(握手成功)的时间，超出该时间抛出connect timeout
                // todo 后面调整从配置中心获取
                .setConnectTimeout(TIME_OUT)
                // 从连接池中获取连接的超时时间，超过该时间未拿到可用连接，
                // 会抛出org.apache.http.conn.ConnectionPoolTimeoutException:
                // Timeout waiting for connection from pool
                // todo 后面调整从配置中心获取
                .setConnectionRequestTimeout(TIME_OUT).build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)
                .setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                .setConnectionReuseStrategy(new DefaultClientConnectionReuseStrategy())
                .build();
    }

}
