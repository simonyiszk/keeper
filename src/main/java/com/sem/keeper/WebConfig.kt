package com.sem.keeper

import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Description
import org.springframework.context.support.ResourceBundleMessageSource
import org.springframework.web.servlet.LocaleResolver
import org.springframework.web.servlet.ViewResolver
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor
import org.springframework.web.servlet.i18n.SessionLocaleResolver
import org.thymeleaf.spring5.SpringTemplateEngine
import org.thymeleaf.spring5.view.ThymeleafViewResolver
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver
import java.util.*


@Configuration
//@EnableWebMvc
open class WebConfig : WebMvcConfigurer {
    @Bean
    open fun localeResolver(): LocaleResolver {
        val localeResolver = SessionLocaleResolver()
        localeResolver.setDefaultLocale(Locale.ENGLISH)
        return localeResolver
    }

    @Bean
    open fun localeChangeInterceptor(): LocaleChangeInterceptor {
        val localeChangeInterceptor = LocaleChangeInterceptor()
        localeChangeInterceptor.paramName = "language"
        return localeChangeInterceptor
    }

    @Bean
    open fun messageSource(): MessageSource {
        val messageSource = ResourceBundleMessageSource()
        messageSource.setBasename("i18n/messages")
        messageSource.setDefaultEncoding("UTF-8")
        return messageSource
    }

    @Bean
    @Description("Thymeleaf template resolver serving HTML 5")
    open fun templateResolver(): ClassLoaderTemplateResolver {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = "templates/"
        templateResolver.isCacheable = false
        templateResolver.suffix = ".html"
        templateResolver.setTemplateMode("HTML5")
        templateResolver.characterEncoding = "UTF-8"
        return templateResolver
    }

    @Bean
    @Description("Thymeleaf template engine with Spring integration")
    open fun templateEngine(): SpringTemplateEngine {
        val templateEngine = SpringTemplateEngine()
        templateEngine.setTemplateResolver(templateResolver())
        return templateEngine
    }

    @Bean
    @Description("Thymeleaf view resolver")
    open fun viewResolver(): ViewResolver {
        val viewResolver = ThymeleafViewResolver()
        viewResolver.templateEngine = templateEngine()
        viewResolver.characterEncoding = "UTF-8"
        return viewResolver
    }

    override fun addViewControllers(registry: ViewControllerRegistry) {
        //
        // registry.addViewController("/profile").setViewName("profile");
    }

    override fun addInterceptors(registry: InterceptorRegistry) {
        //registry.addInterceptor(new UserDataAdder()).excludePathPatterns("/static/*","/ajax/**","/error");
    }
}