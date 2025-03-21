package com.parcel.tracking.service;

import com.parcel.tracking.dto.TrackingRequestDTO;
import com.parcel.tracking.dto.TrackingResponseDTO;
import com.parcel.tracking.entity.TrackParcel;
import com.parcel.tracking.repository.TrackingParcelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

    @ExtendWith(MockitoExtension.class)
    class TrackingParcelServiceTest {

        @Mock
        private TrackingParcelRepository repository;

        @InjectMocks
        private TrackingParcelService service;

        private TrackingRequestDTO requestDTO;
        private TrackParcel savedTrackingNumber;

        @BeforeEach
        void setUp() {
            requestDTO = new TrackingRequestDTO();
            requestDTO.setOriginCountryId("US");
            requestDTO.setDestinationCountryId("CA");
            requestDTO.setWeight(1.5);
            requestDTO.setCustomerId(UUID.randomUUID());
            requestDTO.setCustomerName("RedBox Logistics");
            requestDTO.setCustomerSlug("redbox-logistics");

            savedTrackingNumber = new TrackParcel();
            savedTrackingNumber.setId(UUID.randomUUID());
            savedTrackingNumber.setTrackingNumber("USCA1234567890");
            savedTrackingNumber.setOriginCountryId(requestDTO.getOriginCountryId());
            savedTrackingNumber.setDestinationCountryId(requestDTO.getDestinationCountryId());
            savedTrackingNumber.setWeight(requestDTO.getWeight());
            savedTrackingNumber.setCustomerId(requestDTO.getCustomerId());
            savedTrackingNumber.setCustomerName(requestDTO.getCustomerName());
            savedTrackingNumber.setCustomerSlug(requestDTO.getCustomerSlug());
        }

        @Test
        void testGenerateAndSaveTrackingNumber_Success() {
            when(repository.findByTrackingNumber(anyString())).thenReturn(Optional.empty());
            when(repository.save(any(TrackParcel.class))).thenReturn(savedTrackingNumber);

            TrackingResponseDTO response = service.generateAndSaveTrackingNumber(requestDTO);

            assertNotNull(response);
            assertEquals("USCA1234567890", response.getTrackingNumber());
            assertEquals("US", response.getOriginCountryId());
            assertEquals("CA", response.getDestinationCountryId());
            verify(repository, times(1)).save(any(TrackParcel.class));
        }

        @Test
        void testGenerateAndSaveTrackingNumber_DuplicateGeneration() {
            // ✅ Simulate duplicate tracking number scenario
            when(repository.findByTrackingNumber(anyString()))
                    .thenReturn(Optional.of(savedTrackingNumber)) // First generated tracking number is already taken
                    .thenReturn(Optional.of(savedTrackingNumber)) // Second attempt also taken (force retry)
                    .thenReturn(Optional.empty()); // Finally, a unique tracking number is found

            // ✅ Simulate database integrity constraint failure on `save()`
            when(repository.save(any(TrackParcel.class)))
                    .thenThrow(new DataIntegrityViolationException("Duplicate tracking number detected"));

            IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                    service.generateAndSaveTrackingNumber(requestDTO)
            );

            assertEquals("Duplicate tracking number generated. Please retry.", exception.getMessage());

            // ✅ Verify that multiple attempts were made to generate a unique tracking number
            verify(repository, atLeast(2)).findByTrackingNumber(anyString());

            // ✅ Ensure the service **attempted** to save, but it failed
            verify(repository, times(1)).save(any(TrackParcel.class));
        }
    }
