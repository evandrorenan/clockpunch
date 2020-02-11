package br.com.evandrorenan.learning.clockpunch.doc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.evandrorenan.learning.clockpunch.utils.ClockPunchUtils;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Autowired
	private MessageSource messageSource;
	
    private ApiInfo apiInfo() {
    	
    	String title 				= ClockPunchUtils.getMessage("swagger.application.title", 				messageSource);
    	String description 			= ClockPunchUtils.getMessage("swagger.application.description", 		messageSource);
    	String license 				= ClockPunchUtils.getMessage("swagger.application.license", 			messageSource);
    	String licenseUrl 			= ClockPunchUtils.getMessage("swagger.application.licenseUrl", 			messageSource);
    	String termsOfServiceUrl 	= ClockPunchUtils.getMessage("swagger.application.termsOfServiceUrl", 	messageSource);
    	String version 				= ClockPunchUtils.getMessage("swagger.application.version", 			messageSource);
      	 
        return new ApiInfoBuilder()
				.title(title)
				.description(description)
				.license(license)
				.licenseUrl(licenseUrl)
				.termsOfServiceUrl(termsOfServiceUrl)
				.version(version)
                .build();
    }
    
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo());
	}
}
