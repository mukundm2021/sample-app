package com.hcl.sample.controller;

import com.hcl.sample.model.Product;

import com.hcl.sample.service.SearchService;
import com.hcl.sample.utility.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {
    private static final Logger logger = LogManager.getLogger(SearchController.class);
    @Autowired
    SearchService searchService;

    @GetMapping("/products/{productName}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String productName) throws ResourceNotFoundException{
        logger.debug("------ search existing product  by Product Name   Operation  for ProductName  " +productName + " -------");
     List<Product> existingProducts =searchService.searchProductsByName(productName);


     if(existingProducts!=null && existingProducts.size()>0){

         return new ResponseEntity<List<Product>>(existingProducts, HttpStatus.OK);
     } else {
         logger.error("------ Exception occurred while searching existing product  by Product Name  " +productName + " -------");
         throw new ResourceNotFoundException("No Product  Found  of Product Name " + productName);
     }

    }

    @GetMapping("/products/manufacturerRegion/{manufacturerRegion}")
    public ResponseEntity<List<Product>> getProductByRegion(@PathVariable String manufacturerRegion) throws  ResourceNotFoundException{
        logger.debug(" ----------   Search product by manufacturing region  ----------------------" + manufacturerRegion);
        List<Product> products =searchService.searchProductsByRegion(manufacturerRegion);
        if(products!=null && products.size()>0){
            logger.debug("------ Search  manufacturers by Manufacturer Region operation  for manufacturerRegion ------ " + manufacturerRegion);
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        } else {
            logger.error("Exception occurred while searching  product for given manufacturer region   " + manufacturerRegion);
            throw new ResourceNotFoundException("No Products  Found  for given manufacturer region ");
        }

    }


    @GetMapping("/products/manufacturingDate/{manufacturingDate}")
    public ResponseEntity<List<Product>> getProductByManufacturingDate(@PathVariable  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate manufacturingDate){
        logger.debug(" ----------   Search product by manufacturing date ----------------------");
        List<Product> products =searchService.searchProductsByManufacturingDate(manufacturingDate);
        if(products!=null && products.size()>0){
            logger.debug("------ Search  manufacturers by Manufacturing  Date operation  for Manufacturing date ------ " + manufacturingDate);
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        } else {
            logger.error("Exception occurred while searching  product for given manufacturing date   " + manufacturingDate);
            throw new ResourceNotFoundException("No Products  Found  for given manufacturing  date  ");
        }

    }


    @GetMapping("/products/manufacturerCountry/{manufacturerCountry}")
    public ResponseEntity<List<Product>> getProductByManufacturerCountry(@PathVariable String manufacturerCountry) {
        List<Product> products = searchService.searchProductsByManufacturerCountry(manufacturerCountry);
        if (products != null && products.size() > 0) {
            logger.debug("------ Search  manufacturers by Manufacturer  country  operation  for Manufacturer country  ------ " + manufacturerCountry);
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        } else {
            logger.error("Exception occurred while searching  product for given manufacturer  country   " + manufacturerCountry);
            throw new ResourceNotFoundException("No Products  Found  for given manufacturer country ");
        }
    }


        @GetMapping("/products/manufacturers/{manufacturingDate},{manufacturerCountry},{manufacturerRegion}")
        public ResponseEntity<List<Product>> getProductByMultiSearchCriteria ( @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate manufacturingDate,@PathVariable String
        manufacturerCountry, @PathVariable String  manufacturerRegion){
            logger.debug("-------------Search product my multiple search criteria  manufacturing date , country and region All fields are required ----------   " );
            List<Product> products = searchService.searchProductsByManufacturingDateCountryRegion(manufacturingDate,manufacturerCountry, manufacturerRegion);
            if (products != null && products.size() > 0) {
                logger.debug("------ Search  manufacturers by Manufacturing date  And   country  And region   operation ");
                return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
            } else {
                logger.error("Exception occurred while searching  product for given manufacturing date ,  country and region    " );
                throw new ResourceNotFoundException("No Products  Found  for given manufacturing date country and region ");
            }

        }


    @GetMapping("/products/manufacturers/multiSearch/{manufacturingDate},{manufacturerCountry},{manufacturerRegion}")
    public ResponseEntity<List<Product>> getProductByOptionalMultiCriteria (@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate manufacturingDate,@RequestParam(required = false) String
            manufacturerCountry, @RequestParam(required = false) String  manufacturerRegion){
        logger.debug("-------------Search product my multiple search criteria  manufacturing date , country or  region any one fields ----------   " );
        List<Product> products = searchService.searchProductsByManufacturingDateOrCountryOrRegion(manufacturingDate,manufacturerCountry, manufacturerRegion);
        if (products != null && products.size() > 0) {
            logger.debug("------ Search  manufacturers by Manufacturing  search criteria  manufacturing date or country  or  region  any one ------ ");
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        } else {
            logger.error("Exception occurred while searching  product for given manufacturing date ,  country or region    " );
            throw new ResourceNotFoundException("No Products  Found  for given manufacturing date country or  region ");
        }

    }
}
