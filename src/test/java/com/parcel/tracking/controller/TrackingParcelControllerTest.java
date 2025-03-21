package com.parcel.tracking.controller;

import com.parcel.tracking.dto.TrackingRequestDTO;
import com.parcel.tracking.dto.TrackingResponseDTO;
import com.parcel.tracking.service.TrackingParcelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
    class TrackingParcelControllerTest {

        @Mock
        private TrackingParcelService service;

        @InjectMocks
        private TrackingParcelController controller;

        private TrackingRequestDTO requestDTO;
        private TrackingResponseDTO responseDTO;

        @BeforeEach
        void setUp() {
            requestDTO = new TrackingRequestDTO();
            requestDTO.setOriginCountryId("US");
            requestDTO.setDestinationCountryId("CA");
            requestDTO.setWeight(1.5);
            requestDTO.setCustomerId(UUID.randomUUID());
            requestDTO.setCustomerName("RedBox Logistics");
            requestDTO.setCustomerSlug("redbox-logistics");

            responseDTO = new TrackingResponseDTO();
            responseDTO.setTrackingNumber("USCA1234567890");
        }

        @Test
        void testGenerateTrackingNumber_Success() {
            when(service.generateAndSaveTrackingNumber(any())).thenReturn(responseDTO);

            TrackingResponseDTO result = controller.generateTrackingNumber(requestDTO);

            assertNotNull(result);
            assertEquals("USCA1234567890", result.getTrackingNumber());
            verify(service, times(1)).generateAndSaveTrackingNumber(any());
        }

        @Test
        void testGenerateTrackingNumber_DuplicateRequest() {
            when(service.generateAndSaveTrackingNumber(any())).thenThrow(new IllegalStateException("Tracking number with this combination already exists"));

            IllegalStateException exception = assertThrows(IllegalStateException.class, () ->
                    controller.generateTrackingNumber(requestDTO)
            );

            assertEquals("Tracking number with this combination already exists", exception.getMessage());
        }
    }
