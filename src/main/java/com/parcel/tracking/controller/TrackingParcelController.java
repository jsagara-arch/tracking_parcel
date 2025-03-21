package com.parcel.tracking.controller;

import com.parcel.tracking.dto.TrackingRequestDTO;
import com.parcel.tracking.dto.TrackingResponseDTO;
import com.parcel.tracking.service.TrackingParcelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Tag(name = "Tracking Parcel API", description = "API for generating tracking parcel")
public class TrackingParcelController {

	@Autowired
	private TrackingParcelService trackingParcelService;


	@PostMapping("/next-tracking-number")
	@Operation(summary = "Generate a unique tracking number", description = "Generates a unique tracking number based on the provided parameters.")
	public TrackingResponseDTO generateTrackingNumber(@RequestBody @Valid TrackingRequestDTO request) {
		return trackingParcelService.generateAndSaveTrackingNumber(request);
	}
}