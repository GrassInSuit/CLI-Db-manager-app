package org.example;

public class itemStock {
    private int quantity_delta;
    private String location_id;
    private String movement_type;
    private String reference_id;
    private String note;
    private String date;
    public itemStock(String name, int quantity_delta, String location_id, String movement_type , String reference_id, String note , String date){
        this.quantity_delta=quantity_delta;
        this.location_id=location_id;
        this.movement_type=movement_type;
        this.reference_id=reference_id;
        this.note=note;
        this.date=date;
    }

    public int getQuantityDelta() {
        return this.quantity_delta;
    }

    public String getLocation() {
        return this.location_id;
    }

    public String getMovementType() {
        return this.movement_type;
    }

    public String getReference_id(){return reference_id;}

    public String getNote(){return note;}

    public String getDate() {
        return this.date;
    }


}
