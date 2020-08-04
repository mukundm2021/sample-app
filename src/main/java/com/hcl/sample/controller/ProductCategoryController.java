package com.hcl.sample.controller;

import java.util.ArrayList;
import java.util.List;
import com.hcl.sample.model.ProductCategory;
import com.hcl.sample.service.ProductCategoryService;
import com.hcl.sample.utility.AppServiceException;
import com.hcl.sample.utility.ProductCategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@RestController
@RequestMapping("/productCategories")
public class ProductCategoryController {

    private static final Logger logger = LogManager.getLogger(ProductCategoryController.class);
    @Autowired
    ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getProductCategories(){
        List<ProductCategory> existingProductCategories =null ;
        logger.debug("--------------Search product category---------------------- " );
        try{
           existingProductCategories= productCategoryService.getProductCategories();
           if(!existingProductCategories.isEmpty()){
               return new ResponseEntity<List<ProductCategory>>(existingProductCategories ,HttpStatus.OK);
           }
           else{
               logger.debug("----------- No Product categories found ---------- ");
               return new ResponseEntity<List<ProductCategory>>(HttpStatus.NOT_FOUND);
           }
        }

        catch (ProductCategoryNotFoundException e)
        {
            logger.error("-------- Exception occurred while searching product category-------------- " , e);
            return new ResponseEntity<List<ProductCategory>>(HttpStatus.INTERNAL_SERVER_ERROR);
        }



    }

    @GetMapping("{productCategoryId}")
    public ResponseEntity<ProductCategory> getProductCategoryById(@PathVariable Integer productCategoryId) {

        ProductCategory productCategory;
        logger.debug("-----------------------Search product category--------------------- " );
        try {
            productCategory = productCategoryService.getProductCategoryById(productCategoryId);
        }

        catch (ProductCategoryNotFoundException e)
        {
            logger.error("Exception occurred while searching product category  by id " , e);
            return new ResponseEntity<ProductCategory>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<ProductCategory>(productCategory ,HttpStatus.OK);
    }


    @PostMapping()
    public ResponseEntity<List<ProductCategory>>  addProductCategory(@RequestBody List<ProductCategory> productCategoryList) {

        List<ProductCategory> prodCategoryCategoryList = new ArrayList<ProductCategory>();
        logger.debug("--------------  Create  product categories operations ------------------- " );
        try {
            prodCategoryCategoryList = productCategoryService.createProductCategory(productCategoryList);
        }
         catch (ProductCategoryNotFoundException e)
        {
            logger.error("Exception occurred while creating product category " , e);
            return new ResponseEntity<List<ProductCategory>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<ProductCategory>>( prodCategoryCategoryList,HttpStatus.OK);
    }

    /*  adding  one product category  can be done by passing only one productCategory in list */
    /* @PostMapping("/productCategory")
        public ResponseEntity<ProductCategory> addProductCategory(@RequestBody ProductCategory productCategory) {
        ProductCategory prodCategoryCategory =null;
        logger.debug(" Create  product category " );
        try {
            prodCategoryCategory = productCategoryService.createProductCategory(productCategory);
        }
        catch (AppServiceException e)
        {
            logger.error("Exception occurred while creating product category " , e);
            return new ResponseEntity<ProductCategory>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductCategory>( prodCategoryCategory,HttpStatus.OK);
    }
*/

    @PutMapping("/{productCategoryId}")
    public ResponseEntity<ProductCategory> updateProductCategory(@RequestBody ProductCategory productCategory , @PathVariable int productCategoryId ) {

        try {
            ProductCategory productCategory1 = productCategoryService.getProductCategoryById(productCategoryId);
        } catch (ProductCategoryNotFoundException e) {
            logger.error("Exception occurred while updating  product category " , e);
            return new ResponseEntity<ProductCategory>(HttpStatus.NOT_FOUND);
        }

        ProductCategory updatedProductCategory =null;
        productCategory.setProductCategoryId(productCategoryId);
        logger.debug("--------------------  Update product category operation ----------------------- " );
        try {
            updatedProductCategory = productCategoryService.updateProductCategory(productCategory);
        }

        catch (AppServiceException  e)
        {
            logger.error("Exception occurred while updating  product category " , e);
            return new ResponseEntity<ProductCategory>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ProductCategory>(updatedProductCategory,HttpStatus.OK);
    }
    @DeleteMapping("{productCatId}")
    public ResponseEntity<String> deleteProductCategory(@PathVariable int productCatId  ) {
        ProductCategory productCategoryToBeDeleted;

        logger.debug(" -----------------  Delete  product category  Operation -------------------");

        try {
             productCategoryToBeDeleted  = productCategoryService.getProductCategoryById(productCatId);
        } catch (ProductCategoryNotFoundException e) {
            logger.error(" Exception while deleting product category " , e);
            return  new ResponseEntity<String>("Product category  can not be not deleted ",HttpStatus.NOT_FOUND);
        }
        try {
            productCategoryService.deleteProductCategory(productCategoryToBeDeleted);
        }
        catch(Exception e ){
            logger.error(" Exception while deleting product category " , e);
            return  new ResponseEntity<String>("Product category  can not be not deleted ",HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return  new ResponseEntity<String>("Product category  deleted successfully",HttpStatus.OK);

    }

}
