package com.parcel.tracking.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tracking_parcel", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "originCountryId",
                "destinationCountryId",
                "weight",
                "customerId"
        })
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TrackParcel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, length = 16, nullable = false)
    private String trackingNumber;

    @Column(nullable = false)
    private String originCountryId;

    @Column(nullable = false)
    private String destinationCountryId;

    @Column(nullable = false)
    private Double weight;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @Column(nullable = false)
    private UUID customerId;

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String customerSlug;
}

