package com.parcel.tracking.service;

import com.parcel.tracking.dto.TrackingRequestDTO;
import com.parcel.tracking.dto.TrackingResponseDTO;
import com.parcel.tracking.entity.TrackParcel;
import com.parcel.tracking.repository.TrackingParcelRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class TrackingParcelService {

    private final TrackingParcelRepository repository;
    private final SecureRandom random = new SecureRandom();
    private final ConcurrentHashMap<String, Boolean> cache = new ConcurrentHashMap<>();

    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int TRACKING_NUMBER_LENGTH = 16;

    public TrackingParcelService(TrackingParcelRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public TrackingResponseDTO generateAndSaveTrackingNumber(TrackingRequestDTO requestDTO) {
        // ✅ Check if the request already exists
        Optional<TrackParcel> existingRecord = repository.findExistingData(
                requestDTO.getOriginCountryId(),
                requestDTO.getDestinationCountryId(),
                requestDTO.getWeight(),
                requestDTO.getCustomerId()
        );

        if (existingRecord.isPresent()) {
            throw new IllegalStateException("Tracking number with this combination already exists");
        }

        // ✅ Generate a tracking number safely
        String trackingNumber;
        synchronized (this) {
            do {
                trackingNumber = generateTrackingNumber();
            } while (cache.containsKey(trackingNumber) || repository.findByTrackingNumber(trackingNumber).isPresent());
            cache.put(trackingNumber, true);
        }

        try {
            TrackParcel entity = new TrackParcel();
            entity.setTrackingNumber(trackingNumber);
            entity.setOriginCountryId(requestDTO.getOriginCountryId());
            entity.setDestinationCountryId(requestDTO.getDestinationCountryId());
            entity.setWeight(requestDTO.getWeight());
            entity.setCustomerId(requestDTO.getCustomerId());
            entity.setCustomerName(requestDTO.getCustomerName());
            entity.setCustomerSlug(requestDTO.getCustomerSlug());

            // ✅ Save to DB and return exact stored data
            entity = repository.save(entity);
            repository.flush();
            cache.remove(trackingNumber);

            return new TrackingResponseDTO(entity);
        } catch (DataIntegrityViolationException e) {
            cache.remove(trackingNumber);
            throw new IllegalStateException("Duplicate tracking number generated. Please retry.");
        }
    }

    private String generateTrackingNumber() {
        StringBuilder sb = new StringBuilder(TRACKING_NUMBER_LENGTH);
        for (int i = 0; i < TRACKING_NUMBER_LENGTH; i++) {
            sb.append(CHAR_POOL.charAt(random.nextInt(CHAR_POOL.length())));
        }
        return sb.toString();
    }

}