package com.hcl.sample;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;


@EnableSwagger2
@SpringBootApplication
public class SampleAppApplication {  //  extends SpringBootServletInitializer { // uncomment for external server deployment

	public static void main(String[] args) {
		SpringApplication.run(SampleAppApplication.class, args);
	}

	@Bean
	public Docket applicationApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select().apis(RequestHandlerSelectors.basePackage("com.hcl.sample")).build()
				.apiInfo(metaInfo());
	}


	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo("Apple Training Project  Product API ",
				" Apple training Project  product  category , product , manufacturers  sellers and Search API ",
				"1.0 ",
				"terms of service ",
				new Contact(" Mukund M  ", " https://www.hcltech.com", "mukund.murari@hcl.com"),
				" My software licence ",
				"https://www.hcltech.com",
				new ArrayList());

		return apiInfo;
	}

	/*	uncomment  for external server deployment using jenkins     */

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(SampleAppApplication.class);
	}
	*/


}
