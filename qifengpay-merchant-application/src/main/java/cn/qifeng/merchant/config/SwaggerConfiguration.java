package cn.qifeng.merchant.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@ConditionalOnProperty(prefix = "swagger",value = {"enable"},havingValue = "true")
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket buildDocket(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(QifengApiInfo())
                .select()
                //要扫描的api包（controller）
                .apis(RequestHandlerSelectors.basePackage("cn.qifeng.merchant.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo QifengApiInfo() {
        Contact contact=new Contact("Fruit","965921784@qq.com","965921784@qq.com");
        return new ApiInfoBuilder()
                .title("启峰api文档")
                .description("")
                .contact(contact)
                .version("1.0.1").build();


    }
}
