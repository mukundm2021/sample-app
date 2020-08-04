package com.hcl.sample.controller;

import com.hcl.sample.model.Product;
import com.hcl.sample.model.Seller;
import com.hcl.sample.service.ProductService;
import com.hcl.sample.service.SellerService;
import com.hcl.sample.utility.AppServiceException;
import com.hcl.sample.utility.ResourceNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SellerController {

    private static final Logger logger = LogManager.getLogger(ProductController.class);

    @Autowired
    SellerService sellerService;

    @Autowired
    ProductService productService;


    @GetMapping("/sellers")
    public ResponseEntity<List<Seller>> getAllSellers() throws ResourceNotFoundException {
        List<Seller> sellers = sellerService.getAllSellers();
        logger.debug(" -----------------search  all existing sellers----------------------------------");
        if (sellers != null && sellers.size() > 0) {
            return new ResponseEntity<List<Seller>>(sellers, HttpStatus.OK);
        } else {
            logger.debug("----------- No sellers    found -------------------- ");
            throw new ResourceNotFoundException("No  sellers   Found ");
        }
    }

    @GetMapping("/products/{productId}/sellers")
    public ResponseEntity<List<Seller>> getSellersByProductId(@PathVariable int productId) throws  ResourceNotFoundException {
        logger.debug(" -----------------search  all existing sellers for specific  product  with productId  " +productId + " ------------");
        List<Seller> sellers = sellerService.getSellerByProductId(productId);
        if (sellers != null && sellers.size() > 0) {
            return new ResponseEntity<List<Seller>>(sellers, HttpStatus.OK);
        } else {
            logger.debug("----------- No sellers    found  for Product with productId -------------------- " + productId);
            throw new ResourceNotFoundException("No  sellers   Found for Product with ProductId  " + productId);
        }
    }

    @GetMapping("/sellers/{sellerId}")
    public ResponseEntity<Seller> getSellerById(@PathVariable Integer sellerId) throws ResourceNotFoundException {
        logger.debug("-----------search seller by sellerId  Operation " + sellerId);
        Seller seller = sellerService.getSellerById(sellerId);

        if (seller != null) {
            return new ResponseEntity<Seller>(seller, HttpStatus.OK);
        } else {
            logger.debug("----------- No sellers    found  for  sellerId -------------------- " + sellerId);
            throw new ResourceNotFoundException("No  sellers   Found for sellerId  " + sellerId);
        }
    }

    @PostMapping("/products/{productId}/sellers")
    public ResponseEntity<Seller> addNewSellerForProductId(@RequestBody Seller seller, @PathVariable int productId) throws AppServiceException  {
        logger.debug(" ----------------- Create   seller  Operation  ------------");
        Product product = productService.getProductById(productId);
        seller.setProduct(product);
        Seller newSeller = sellerService.createSeller(seller);

        if (newSeller != null) {
            return new ResponseEntity<>(newSeller, HttpStatus.CREATED);
        } else {
            logger.error("-------------Exception occurred while creating seller   for  Product ------------ " + productId);
            throw new AppServiceException(" error occurred while creating seller -------------");
        }
    }


    @PutMapping("/sellers/{sellerId}")
    public ResponseEntity<Seller> updateSeller(@RequestBody Seller seller, @PathVariable int sellerId)  throws AppServiceException, ResourceNotFoundException {
        logger.debug("----------- update seller for sellerId operation -------------------- " + sellerId);
        Seller existingSeller = sellerService.getSellerById(sellerId);
        if (existingSeller != null) {
            Seller updatedSeller = sellerService.updateSeller(seller);

            if (updatedSeller != null) {
                return new ResponseEntity<>(updatedSeller, HttpStatus.OK);
            } else {
                logger.error("-------------Exception occurred while updating  manufacturer  ------------ ");
                throw new AppServiceException(" error occurred while updating  manufacturer  with manufacturerId  -------------" + sellerId);
            }
        } else {
            logger.debug("----------- No sellers    found  for  update for sellerId -------------------- " + sellerId);
            throw new ResourceNotFoundException("No  sellers   Found for  update for  sellerId  " + sellerId);
        }
    }


    @DeleteMapping("/sellers{sellerId}")
    public ResponseEntity<String> deleteSeller(@RequestBody Seller seller, @PathVariable int sellerId) throws ResourceNotFoundException {
        logger.debug(" ----------------- Delete   Seller  Operation  ------------");
        Seller sellerToBeDeleted = sellerService.getSellerById(sellerId);
        if (sellerToBeDeleted != null) {
            sellerService.deleteSeller(seller);
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Seller  deleted successfully");
        } else {
            logger.debug("-----------  Can not delete seller No sellers  is available for sellerId  " + sellerId);
            throw new ResourceNotFoundException("No  seller    Found ");
        }

    }
}
