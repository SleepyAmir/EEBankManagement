package com.sleepy.eebankmanagement.model.entity.config;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.WebApplicationTemplateResolver;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

@WebListener
public class ThymeleafConfig implements ServletContextListener {
    private JakartaServletWebApplication application;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();

        this.application = JakartaServletWebApplication.buildApplication(servletContext);


        WebApplicationTemplateResolver templateResolver = new WebApplicationTemplateResolver(this.application);

        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setCacheable(false);

        TemplateEngine templateEngine =  new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        servletContext.setAttribute("application", application);
        servletContext.setAttribute("templateEngine", templateEngine);
    }

    public JakartaServletWebApplication getApplication() {
        return application;
    }
}
