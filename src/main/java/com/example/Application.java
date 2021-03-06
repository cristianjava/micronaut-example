package com.example;

import com.example.services.HelloWorldService;
import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@OpenAPIDefinition(
        info = @Info(
                title = "micronaut application",
                version = "0.1",
                description = "integration with AWS",
                license = @License(name = "MIT")
        )
)
public class Application {

    private static final Logger LOG = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        //Micronaut.run(Application.class, args);

        final ApplicationContext context = Micronaut.run(Application.class, args);
        final HelloWorldService helloWorldService = context.getBean(HelloWorldService.class);
        LOG.info(helloWorldService.hello());

    }
}
