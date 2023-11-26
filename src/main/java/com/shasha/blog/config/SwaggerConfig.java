package com.shasha.blog.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket apiDoc() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(getInfo())
				.securityContexts(securityContext())
				.securitySchemes(Arrays.asList(apiKeys()))
		
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any()).build();

	}
	
	private List<SecurityContext> securityContext(){
		return Arrays.asList(SecurityContext.builder()
				.securityReferences(securityReferences()).build());
	}
	
	private  List<SecurityReference> securityReferences(){
		AuthorizationScope scope=new AuthorizationScope(AppConstants.GLOBAL, AppConstants.ACCESS);
		return Arrays.asList(new SecurityReference(AppConstants.JWT, new AuthorizationScope[]{scope}));
		
	}
private ApiKey apiKeys() {
	return new ApiKey(AppConstants.JWT, AppConstants.AUTHORIZE_HEADER, AppConstants.HEADER);
}
	
	
	private ApiInfo getInfo() {

		return new ApiInfo(AppConstants.TITLE, AppConstants.DESCRIPTION, AppConstants.VERSION,
				AppConstants.LICENSE,AppConstants.NUM, AppConstants.GMAIL,
				AppConstants.LICENSE);
	}
}
