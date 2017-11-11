package md.pad;

import md.pad.model.db.Season;
import md.pad.model.db.Serial;
import md.pad.model.db.Series;
import md.pad.service.SeasonService;
import md.pad.service.SerialService;
import md.pad.service.SeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import java.time.LocalDate;
import java.util.List;

@EntityScan(
        basePackageClasses = {LabPadGroupApplication.class, Jsr310JpaConverters.class}
)
@SpringBootApplication
public class LabPadGroupApplication
{

    public static void main(final String[] args)
    {
        SpringApplication.run(LabPadGroupApplication.class, args);
    }
}
