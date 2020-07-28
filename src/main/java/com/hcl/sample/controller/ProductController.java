package com.hcl.sample.controller;

import com.hcl.sample.model.Product;
import com.hcl.sample.model.ProductCategory;
import com.hcl.sample.service.ManufacturerService;
import com.hcl.sample.service.ProductCategoryService;
import com.hcl.sample.service.ProductService;
import com.hcl.sample.utility.AppServiceException;
import com.hcl.sample.utility.ProductCategoryNotFoundException;
import com.hcl.sample.utility.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    private Logger logger = (Logger) LoggerFactory.getLogger(ProductController.class);

    @Autowired
    ProductService productService;
    @Autowired
    ProductCategoryService productCategoryService;
    @Autowired
    ManufacturerService manufacturerService;


    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts() throws  ResourceNotFoundException{

        List<Product> existingProducts = productService.getAllProducts();
        if (existingProducts != null) {
            logger.debug("Search existing  product ");

            return new ResponseEntity<List<Product>>(existingProducts, HttpStatus.OK);
        } else {
            logger.error("Exception occurred while searching existing product  ");
            throw new ResourceNotFoundException("No Product  Found ");
        }

    }


    @GetMapping("/products/{productId}")
    public ResponseEntity<Product> getProductByProductId(@PathVariable Integer productId)  throws  ResourceNotFoundException{
        Product product = productService.getProductById(productId);
        if (product != null) {
            logger.debug("Search  product for Product Id  " + productId);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        } else {
            logger.error("Exception occurred while searching existing product  ");
            throw new ResourceNotFoundException("No Product  Found ");
        }

    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(@RequestBody int productId)  throws  ResourceNotFoundException{
        Product product = productService.getProductById(productId);
        if (product != null) {
            logger.debug("delete   product for Product Id  " + productId);
            productService.deleteProduct(product);
            return ResponseEntity.status(HttpStatus.OK).body("Product deleted successfully");
        } else {
            logger.error("Exception occurred while deleting  product  with productId  " + productId);
            throw new ResourceNotFoundException("No Product  Found  for product Id " + productId);
        }

    }

    @GetMapping("/productCategories/{productCategoryId}/products")
    public ResponseEntity<List<Product>> getAllProductByProductCategoryId(@PathVariable(value = "productCategoryId") int productCategoryId)  throws  ResourceNotFoundException{
        List<Product> products ;
        products = productService.getProductByProductCategoryId(productCategoryId);
        if (products != null) {
            logger.debug(" get Product by product category " + productCategoryId);
            return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
        } else {
            logger.error("Exception occurred while searching  product by  product category Id   " + productCategoryId);
            throw new ResourceNotFoundException("No Product  Found  for product category Id " +productCategoryId);
        }


    }

    @PostMapping("/productCategories/{productCategoryId}/products")
    public ResponseEntity<Product> createProduct(@PathVariable(value = "productCategoryId") int productCategoryId, @RequestBody Product product)  throws  AppServiceException{
        ProductCategory productCategory = null;
        try {
            productCategory = productCategoryService.getProductCategoryById(productCategoryId);
        } catch (ProductCategoryNotFoundException e) {
            logger.error("Exception occurred while adding new  product for  product category Id   " + productCategoryId + " product category does not exist");
        }
        product.setProductCategory(productCategory);
        Product newProduct = productService.createProduct(product);
        if (newProduct != null) {
            return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
        } else {
            logger.error("-------------Exception occurred while creating Product------------ ");
            throw new AppServiceException(" error occurred while creating product -------------");
        }
    }

     @PutMapping("/productCategories/{productCategoryId}/products/{productId}")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product, @PathVariable int productCategoryId, @PathVariable int productId) throws  ResourceNotFoundException {
         ProductCategory productCategory = null;
         try {
             productCategory = productCategoryService.getProductCategoryById(productCategoryId);
         } catch (ProductCategoryNotFoundException e) {
             logger.error("----------Exception occurred while updating  product for  product category Id   " + productCategoryId + " product category does not exist-------------");
         }
         product.setProductCategory(productCategory);

        product.setProductId(productId);
        Product updatedProduct = productService.updateProduct(product);
        if (updatedProduct != null) {
            logger.debug("---------------------Update   product -----------------------------------   ");
            return new ResponseEntity<Product>(updatedProduct, HttpStatus.OK);
        } else {
            logger.error("------------------------Exception occurred while updating  product  information -----------------");
            throw new ResourceNotFoundException("No Product  Found ");
        }

    }

}


