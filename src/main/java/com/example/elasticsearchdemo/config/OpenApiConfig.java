package com.example.elasticsearchdemo.config;

import com.github.xiaoymin.knife4j.spring.configuration.Knife4jProperties;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Elastic search文档")
                        .version("1.0")
                        .contact(new Contact().name("tmjie5200").email("1059819521@qq.com"))
                        .description( "Elastic search示例")
                        .termsOfService("https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/current/connecting.html")
                        .license(new License().name("Apache 2.0")
                                .url("http://doc.xiaominfo.com")));
    }
}
