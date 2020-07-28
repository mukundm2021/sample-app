package com.hcl.sample.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Seller {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
     private int sellerId;
     private String sellerRegion;
    private  String country ;
     private int productSold;
    private LocalDate sellDate;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID", referencedColumnName = "productId")
    private Product product;

   public String getSellerRegion() {
       return sellerRegion;
   }

    public void setSellerRegion(String sellerRegion) {
        this.sellerRegion = sellerRegion;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getProductSold() {
        return productSold;
    }

    public void setProductSold(int productSold) {
        this.productSold = productSold;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDate getSellDate() {
        return sellDate;
    }

    public void setSellDate(LocalDate sellDate) {
        this.sellDate = sellDate;
    }

}
