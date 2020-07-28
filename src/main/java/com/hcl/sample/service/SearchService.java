package com.hcl.sample.service;

import com.hcl.sample.Repository.ManufacturerRepository;
import com.hcl.sample.Repository.ProductRepository;
import com.hcl.sample.model.Manufacturer;
import com.hcl.sample.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SearchService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    ProductService productService;

    public List<Product> searchProductsByName( String productName ){
        return  productRepository.findByProductName( productName );

    }

    public List<Product> searchProductsByRegion(String manufacturerRegion ){
        List<Manufacturer> productManufacturers = getManufacturerByRegion( manufacturerRegion );
        List<Product> products =  getProductsFromManufacturersList(productManufacturers);
       return products;
    }


    public List<Product> searchProductsByManufacturingDate( LocalDate manufacturingDate){
        List<Manufacturer> productManufacturers = getManufacturersByManufacturingDate( manufacturingDate );
        List<Product> products =  getProductsFromManufacturersList(productManufacturers);
        return products;
    }

    public List<Product> searchProductsByManufacturerCountry(String manufacturerCountry){
        List<Manufacturer> productManufacturers = getManufacturersByCountry( manufacturerCountry );
        List<Product> products =  getProductsFromManufacturersList(productManufacturers);
        return products;
    }


    public List<Product> searchProductsByManufacturingDateCountryRegion(LocalDate manufacturingDt,String country, String region){
        List<Manufacturer> productManufacturers = getManufacturersByManufacturingDateCountryRegion( manufacturingDt,country, region );
        List<Product> products =  getProductsFromManufacturersList(productManufacturers);
        return products;
    }

    public List<Product> searchProductsByManufacturingDateOrCountryOrRegion(LocalDate manufacturingDt,String country, String region){
        List<Manufacturer> productManufacturers = getManufacturersByManufacturingDateOrCountryOrRegion( manufacturingDt,country, region );
        List<Product> products =  getProductsFromManufacturersList(productManufacturers);
        return products;
    }

    public List<Manufacturer> getManufacturerByRegion(String manufacturerRegion ){
        return  manufacturerRepository.findByManufacturerRegion( manufacturerRegion );

    }

    public List<Manufacturer> getManufacturersByManufacturingDate(LocalDate manufacturingDate){
        return  manufacturerRepository.findByManufacturingDate( manufacturingDate );

    }

    public List<Manufacturer> getManufacturersByCountry(String manufacturerCountry){
        return  manufacturerRepository.findByManufacturerCountry(manufacturerCountry );

    }

    public List<Manufacturer> getManufacturersByManufacturingDateCountryRegion(LocalDate manufacturingDt,String country, String region){
        return  manufacturerRepository.findByManufacturingDateAndManufacturerCountryAndManufacturerRegion(manufacturingDt,country, region );

    }


    public List<Manufacturer> getManufacturersByManufacturingDateOrCountryOrRegion(LocalDate manufacturingDt,String country, String region){
        return  manufacturerRepository.findByManufacturingDateOrManufacturerCountryOrManufacturerRegion(manufacturingDt,country, region );

    }


    public List<Product> getProductsFromManufacturersList(List<Manufacturer> productManufacturers){
        List<Product> products = new ArrayList<Product>();
        List<Integer> productIds = new ArrayList<Integer>();
        for(Manufacturer manufacturer:productManufacturers){
            if( manufacturer!=null && manufacturer.getProduct()!=null) {
                int productId = manufacturer.getProduct().getProductId();
                productIds.add(productId);
            }
        }
        for(int productId :productIds){
            Product product = productService.getProductById(productId) ;
            products.add(product);
        }
        return  products;
    }
}
