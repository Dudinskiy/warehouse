package org.example.warehouse;

import org.example.warehouse.config.DataSourceConfig;
import org.example.warehouse.config.WebSecurityConfiguration;
import org.example.warehouse.dao.UsersDAOPostgreImpl;
import org.example.warehouse.services.CustomUserDetailsService;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebMVCInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{WebSecurityConfiguration.class, DataSourceConfig.class, UsersDAOPostgreImpl.class, CustomUserDetailsService.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[]{AppConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }
}
