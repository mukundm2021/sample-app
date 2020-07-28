package com.hcl.sample.controller;
import com.hcl.sample.model.Manufacturer;
import com.hcl.sample.model.Product;
import com.hcl.sample.service.ManufacturerService;
import com.hcl.sample.service.ProductService;
import com.hcl.sample.utility.AppServiceException;
import com.hcl.sample.utility.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ManufacturerController {
    private Logger logger = (Logger) LoggerFactory.getLogger(ManufacturerController.class);

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    ProductService productService;

    @GetMapping("products/{productId}/manufacturers")
    public ResponseEntity<List<Manufacturer>> getManufacturers(Integer  productId)  throws  ResourceNotFoundException{
        logger.debug(" -----------------search  all existing Manufacturers  operation  ------------");
        List<Manufacturer> manufacturers =manufacturerService. getManufacturerByProductId(productId);
        if (manufacturers != null && manufacturers.size() > 0) {
            return new ResponseEntity<List<Manufacturer>>(manufacturers ,HttpStatus.OK);
        }
        else{
            logger.debug("----------- No Manufactures   found  for product  with product Id  "+  productId +  " ---------- ");
            throw new ResourceNotFoundException("No  Manufactures   Found ");
        }

    }

    @GetMapping("/manufacturers/{manufacturerId}")
    public ResponseEntity<Manufacturer> getManufacturerById(@PathVariable  Integer manufacturerId) throws ResourceNotFoundException {
        logger.debug(" -----------------search Manufacturer by ManufacturerId  operation  ------------");
        Manufacturer manufacturer = manufacturerService.getManufacturerById(manufacturerId);
        if (manufacturer != null) {
            return new ResponseEntity<Manufacturer>(manufacturer, HttpStatus.OK);
        } else {
            logger.error("Exception occurred while searching existing manufacturer with manufacturer Id  " + manufacturerId );
            throw new ResourceNotFoundException("No manufacturer  Found  for given manufacturerId " + manufacturerId);
        }

    }

    @PostMapping("products/{productId}/manufacturers")
    public ResponseEntity<Manufacturer> addManufacturer4GivenProductId(@RequestBody Manufacturer manufacturer,@PathVariable int productId) throws AppServiceException {
        Product product = productService.getProductById(productId);
        manufacturer.setProduct(product);
        logger.debug(" ----------------- Create   Manufacturer Operation  ------------");
        Manufacturer createdManufacturer = manufacturerService.createManufacturer(manufacturer);

        if (createdManufacturer != null) {
            return new ResponseEntity<>(createdManufacturer, HttpStatus.CREATED);
        } else {
            logger.error("-------------Exception occurred while creating manufacturer  for  Product ------------ " +productId);
            throw new AppServiceException(" error occurred while creating manufacturer -------------");
        }
    }


    @PutMapping("products/{productId}/manufacturers/{manufacturerId}")
    public ResponseEntity<Manufacturer> updateManufacturer(@RequestBody Manufacturer manufacturer  , @PathVariable int productId ,  @PathVariable int manufacturerId ) throws AppServiceException ,ResourceNotFoundException {
        Manufacturer existingManufacturer = manufacturerService.getManufacturerById(manufacturerId);
        if(existingManufacturer!=null ) {
            Product product = productService.getProductById(productId);
            manufacturer.setManufacturerId(manufacturerId);
            manufacturer.setProduct(product);
            logger.debug(" ----------------- Update   Manufacturer Operation  ------------");
            Manufacturer updatedManufacturer = manufacturerService.updateManufacturer(manufacturer);

            if (updatedManufacturer != null) {
                return new ResponseEntity<>(updatedManufacturer, HttpStatus.OK);
            } else {
                logger.error("-------------Exception occurred while updating  manufacturer  ------------ ");
                throw new AppServiceException(" error occurred while updating  manufacturer  with manufacturerId  -------------" + manufacturerId);
            }
        }
        else{
            throw new ResourceNotFoundException(" No manufacturer found for Update for given manufacturerId   -------------" + manufacturerId);
        }
    }


    @DeleteMapping("/manufacturers/{manufacturerId}")
    public ResponseEntity<String> deleteManufacturer(@PathVariable int  manufacturerId) {
        logger.debug(" ----------------- Delete   Manufacturer Operation  ------------");
        Manufacturer manufacturer = manufacturerService.getManufacturerById(manufacturerId);
        if(manufacturer!=null) {
            manufacturerService.deleteManufacturer(manufacturer);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(" Manufacturer deleted successfully");
        }
        else{
            logger.debug("----------- No Manufactures  is available for manufacturerId  " +manufacturerId );
            throw new ResourceNotFoundException("No  Manufactures   Found ");
             }
         }

    }
