package md.pad;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(basePackageClasses = {LabPadGroupApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
@EnableEurekaClient
public class LabPadGroupApplication
{
    public static void main(final String[] args)
    {
        SpringApplication.run(LabPadGroupApplication.class, args);
    }
}
