package cn.qifeng.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Fruit
 * @version 1.0
 * @create 2022/7/26   13:24
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UAABootstrap {
    public static void main(String[] args) {
        SpringApplication.run(UAABootstrap.class,args);
    }
}
