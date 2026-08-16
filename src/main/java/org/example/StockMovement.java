package org.example;

import java.time.LocalDateTime;
import java.util.UUID;

public class StockMovement {

    private UUID id;
    private UUID itemId;
    private UUID locationId;
    private int quantityDelta;
    private String movementType;
    private UUID referenceId;
    private String note;
    private LocalDateTime createdAt;
    private String locationName; // used only by the lighter constructor, for display purposes

    // Full constructor — matches the actual table structure
    public StockMovement(UUID id, UUID itemId, UUID locationId, int quantityDelta,
                         String movementType, UUID referenceId, String note, LocalDateTime createdAt) {
        this.id = id;
        this.itemId = itemId;
        this.locationId = locationId;
        this.quantityDelta = quantityDelta;
        this.movementType = movementType;
        this.referenceId = referenceId;
        this.note = note;
        this.createdAt = createdAt;
    }

    // Lighter constructor — for display/history queries with a joined location name
    public StockMovement(LocalDateTime createdAt, String movementType, int quantityDelta,
                         String locationName, String note) {
        this.createdAt = createdAt;
        this.movementType = movementType;
        this.quantityDelta = quantityDelta;
        this.locationName = locationName;
        this.note = note;
    }

    // getters
    public UUID getId() { return id; }
    public UUID getItemId() { return itemId; }
    public UUID getLocationId() { return locationId; }
    public int getQuantityDelta() { return quantityDelta; }
    public String getMovementType() { return movementType; }
    public UUID getReferenceId() { return referenceId; }
    public String getNote() { return note; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getLocationName() { return locationName; }

    @Override
    public String toString() {
        return createdAt + " | " + movementType + " | " + quantityDelta + " | " +
                (locationName != null ? locationName : "") + " | " + note;
    }
}