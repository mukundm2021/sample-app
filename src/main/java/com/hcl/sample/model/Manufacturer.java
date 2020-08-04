package com.hcl.sample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Manufacturer {

    @Id
   // @GeneratedValue(strategy = GenerationType.IDENTITY)
     private int manufacturerId;
     private String manufacturerRegion;
     private String manufacturerCountry;
     private int productCount;
    private LocalDate manufacturingDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "productId")
    private Product product;

    public String getManufacturerRegion() {
        return manufacturerRegion;
    }

    public void setManufacturerRegion(String manufacturerRegion) {
        this.manufacturerRegion = manufacturerRegion;
    }

    public String getManufacturerCountry() {
        return manufacturerCountry;
    }

    public void setCountry(String manufacturerCountry) {
        this.manufacturerCountry = manufacturerCountry;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

   public Product getProduct() {
       return product;
   }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getManufacturingDate() {
        return manufacturingDate;
    }

    public void setManufacturingDate(LocalDate manufacturingDate) {
        this.manufacturingDate = manufacturingDate;
    }



}
