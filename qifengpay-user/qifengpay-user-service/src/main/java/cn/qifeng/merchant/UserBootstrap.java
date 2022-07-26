package cn.qifeng.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Fruit
 * @version 1.0
 * @create 2022/7/26   13:26
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class UserBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(UserBootstrap.class,args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
