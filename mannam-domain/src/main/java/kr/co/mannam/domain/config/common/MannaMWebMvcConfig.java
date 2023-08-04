package kr.co.mannam.domain.config.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // Conponent 스캔을 통해 자동으로 Bean 생성됨
public class MannaMWebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthenticationInterceptor())
                .addPathPatterns("/");
//                .excludePathPatterns("/")
//                .excludePathPatterns("/user/login")
//                .excludePathPatterns("/user/auth/register")
//                .excludePathPatterns("/css/*", "/js/*", "/img/*", "/*.ico", "/error");
    }

    @Value("${resource.path}")
    private String resourcePath;

    @Value("${upload.path}")
    private String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        registry.addResourceHandler(uploadPath)
                .addResourceLocations(resourcePath);
    }
}
