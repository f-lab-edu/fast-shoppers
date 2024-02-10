package com.fastshoppers.common.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	private final JwtTokenInterceptor jwtTokenInterceptor;
	private final AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver;

	@Autowired
	public WebConfig(JwtTokenInterceptor jwtTokenInterceptor,
		AuthenticatedUserArgumentResolver authenticatedUserArgumentResolver) {
		this.jwtTokenInterceptor = jwtTokenInterceptor;
		this.authenticatedUserArgumentResolver = authenticatedUserArgumentResolver;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtTokenInterceptor).addPathPatterns("/api/v1/coupon/*");
	}

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(authenticatedUserArgumentResolver);
	}

}
