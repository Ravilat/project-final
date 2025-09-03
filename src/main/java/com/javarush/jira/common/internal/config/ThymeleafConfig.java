package com.javarush.jira.common.internal.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.FileTemplateResolver;

import java.util.Set;

@Configuration
//http://www.thymeleaf.org/doc/articles/thymeleaf3migration.html
@RequiredArgsConstructor
public class ThymeleafConfig {
    private final AppProperties appProperties;

    // Bean для запуска приложения через Idea

    @Bean
    // Attention: with TemplateEngine clear cache doesn't work
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine engine = new SpringTemplateEngine();
        FileTemplateResolver viewResolver = createTemplateResolver("./resources/view/");
        viewResolver.setCheckExistence(true);
        viewResolver.setOrder(1);
        FileTemplateResolver mailResolver = createTemplateResolver("./resources/mails/");
        mailResolver.setOrder(2);
        engine.setTemplateResolvers(Set.of(viewResolver, mailResolver));
        return engine;
    }

    // Bean для запуска docker-compose (чтобы использовать 1 для всех, нужно вьюшки
    // перемещать в "правильное" место (src/main/resources/template)

//    @Bean
//    public SpringTemplateEngine thymeleafTemplateEngine() {
//        SpringTemplateEngine engine = new SpringTemplateEngine();
//
//        ClassLoaderTemplateResolver viewResolver = createClasspathResolver("view/");
//        viewResolver.setOrder(1);
//
//        ClassLoaderTemplateResolver mailResolver = createClasspathResolver("mails/");
//        mailResolver.setOrder(2);
//
//        engine.setTemplateResolvers(Set.of(viewResolver, mailResolver));
//        return engine;
//    }

    private ClassLoaderTemplateResolver createClasspathResolver(String folder) {
        ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
        resolver.setPrefix(folder);
        resolver.setSuffix(".html");
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCacheable(true);
        resolver.setCharacterEncoding("UTF-8");
        return resolver;
    }


    private FileTemplateResolver createTemplateResolver(String pfx) {
        return new FileTemplateResolver() {{
            setPrefix(pfx);
            setSuffix(".html");
            setTemplateMode(TemplateMode.HTML);
            setCacheable(true);
            setCacheTTLMs(appProperties.getTemplatesUpdateCache().toMillis());
            setCharacterEncoding("UTF-8");
        }};
    }
}
