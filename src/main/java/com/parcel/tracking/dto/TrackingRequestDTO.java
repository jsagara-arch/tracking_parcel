package com.parcel.tracking.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class TrackingRequestDTO {

    @NotBlank(message = "Origin country code is required")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid origin country code format (ISO 3166-1 alpha-2)")
    private String originCountryId;

    @NotBlank(message = "Destination country code is required")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Invalid destination country code format (ISO 3166-1 alpha-2)")
    private String destinationCountryId;

    @Positive(message = "Weight must be a positive number")
    @Digits(integer = 5, fraction = 3, message = "Weight must have up to 3 decimal places")
    private Double weight;

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Customer slug is required")
    @Size(max = 30, message = "Customer slug must not exceed 30 characters")
    @Pattern(regexp = "^[a-z]+(-[a-z]+)*$", message = "Customer slug must be in kebab-case (e.g., 'redbox-logistics')")
    private String customerSlug;
}

