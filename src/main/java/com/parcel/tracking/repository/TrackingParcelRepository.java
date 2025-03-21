package com.parcel.tracking.repository;

import com.parcel.tracking.entity.TrackParcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface TrackingParcelRepository extends JpaRepository<TrackParcel, UUID> {
    Optional<TrackParcel> findByTrackingNumber(String trackingNumber);

    @Query("SELECT t FROM TrackParcel t WHERE t.originCountryId = :originCountryId " +
            "AND t.destinationCountryId = :destinationCountryId " +
            "AND t.weight = :weight " +
            "AND t.customerId = :customerId")
    Optional<TrackParcel> findExistingData(
            @Param("originCountryId") String originCountryId,
            @Param("destinationCountryId") String destinationCountryId,
            @Param("weight") Double weight,
            @Param("customerId") UUID customerId);
}
