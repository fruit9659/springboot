package cn.qifeng.merchant;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class PaymentAgentBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(PaymentAgentBootstrap.class, args);
    }

}
