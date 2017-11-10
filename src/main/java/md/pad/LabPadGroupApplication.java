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
public class LabPadGroupApplication implements CommandLineRunner
{

    @Autowired
    private SeasonService seasonService;

    @Autowired
    private SeriesService seriesService;

    @Autowired
    private SerialService serialService;

    public static void main(final String[] args)
    {
        SpringApplication.run(LabPadGroupApplication.class, args);
    }

    @Override public void run(final String... strings) throws Exception
    {
        final List<Serial> all = serialService.getAll();
        System.out.println("asda");
    }
}
