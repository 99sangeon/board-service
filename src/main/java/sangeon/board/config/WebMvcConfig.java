package sangeon.board.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import sangeon.board.intercetor.AddMemberInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        AddMemberInterceptor addMemberInterceptor = new AddMemberInterceptor();
        registry.addInterceptor(addMemberInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/css/**", "/image/**", "/js/**");
    }


}
