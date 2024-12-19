package hello.message;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class ItemConfig {

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

        // 설정파일명 지정 (messages.properties파일, errors.properties파일을 읽겠다는 의미)
        messageSource.setBasenames("messages", "errors");
        messageSource.setDefaultEncoding("utf-8");

        return messageSource;
    }
}
