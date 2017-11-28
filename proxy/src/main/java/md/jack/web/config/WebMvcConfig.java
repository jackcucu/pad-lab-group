package md.jack.web.config;

import md.jack.web.interceptors.AuthenticationInterceptor;
import md.jack.web.validators.ApiValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter
{
    @Autowired
    private ApiValidator apiValidator;

    @Override
    public void addInterceptors(final InterceptorRegistry registry)
    {
        registry.addInterceptor(new AuthenticationInterceptor(apiValidator))
                .addPathPatterns("/api/**");
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
