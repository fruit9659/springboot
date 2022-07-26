package cn.qifeng.merchant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author Fruit
 * @version 1.0
 * @create 2022/7/22   10:09
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TransactionBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(TransactionBootstrap.class,args);
    }
}
