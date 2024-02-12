package com.example.flatservice.core.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(schema = "flats_chooser", name="flats")
public class FlatEntity {
    @Id
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;

    @Column(name = "dt_update")
    private LocalDateTime dtUpdate;

    @Column(name = "offer_type")
    private String offerType;

    private String description;

    private String bedrooms;

    private Integer area;

    private Integer price;

    private Integer floor;

    public FlatEntity() {
    }

    public FlatEntity(UUID uuid,
                      LocalDateTime dtCreate,
                      LocalDateTime dtUpdate,
                      String offerType,
                      String description,
                      String bedrooms,
                      Integer area,
                      Integer price,
                      Integer floor,
                      String[] photoUrls,
                      String originalUrl) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtUpdate = dtUpdate;
        this.offerType = offerType;
        this.description = description;
        this.bedrooms = bedrooms;
        this.area = area;
        this.price = price;
        this.floor = floor;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtUpdate() {
        return dtUpdate;
    }

    public void setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
    }

    public String getOfferType() {
        return offerType;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(String bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getArea() {
        return area;
    }

    public void setArea(Integer area) {
        this.area = area;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }
}
