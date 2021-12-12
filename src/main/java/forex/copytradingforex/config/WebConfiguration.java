package forex.copytradingforex.config;

import forex.copytradingforex.web.interceptor.StatsInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final StatsInterceptor statsInterceptor;
    private final LocaleChangeInterceptor localeChangeInterceptor;

    public WebConfiguration(StatsInterceptor statsInterceptor, LocaleChangeInterceptor localeChangeInterceptor) {
        this.statsInterceptor = statsInterceptor;
        this.localeChangeInterceptor = localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(statsInterceptor);
        registry.addInterceptor(localeChangeInterceptor);
    }
}
