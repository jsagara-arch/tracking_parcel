package com.parcel.tracking.dto;

import com.parcel.tracking.entity.TrackParcel;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;
@Getter
@Setter
public class TrackingResponseDTO {

    private UUID id;
    private String trackingNumber;
    private String originCountryId;
    private String destinationCountryId;
    private Double weight;
    private OffsetDateTime createdAt;
    private UUID customerId;
    private String customerName;
    private String customerSlug;

    public TrackingResponseDTO(TrackParcel entity) {
        this.id = entity.getId();
        this.trackingNumber = entity.getTrackingNumber();
        this.originCountryId = entity.getOriginCountryId();
        this.destinationCountryId = entity.getDestinationCountryId();
        this.weight = entity.getWeight();
        this.createdAt = entity.getCreatedAt();
        this.customerId = entity.getCustomerId();
        this.customerName = entity.getCustomerName();
        this.customerSlug = entity.getCustomerSlug();
    }

    public TrackingResponseDTO() {

    }
}