package com.parcel.tracking;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Tracking API",
				version = "1.0",
				description = "API for Generating Tracking Numbers"
		)
)
public class TrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrackingApplication.class, args);
	}

}
