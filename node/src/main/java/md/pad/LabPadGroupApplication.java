package md.pad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class LabPadGroupApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(LabPadGroupApplication.class, args);
    }
}
