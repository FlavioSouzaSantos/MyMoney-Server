package br.com.mymoney.cadastrationservice;

import br.com.mymoney.crudcommon.controllers.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Import;

@EnableDiscoveryClient
@Import(GlobalExceptionHandler.class)
@SpringBootApplication(scanBasePackages = "br.com.mymoney")
public class CadastrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CadastrationServiceApplication.class, args);
    }

}
