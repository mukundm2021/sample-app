package com.hcl.sample.service;
import com.hcl.sample.Repository.ProductCategoryRepository;
import com.hcl.sample.model.Manufacturer;
import com.hcl.sample.model.Product;
import com.hcl.sample.model.ProductCategory;
import com.hcl.sample.model.Seller;
import com.hcl.sample.utility.ProductCategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ProductCategoryService {

    private Logger logger = (Logger) LoggerFactory.getLogger(ProductCategoryService.class);

    @Autowired
    ProductCategoryRepository  productCategoryRepo;
    @Autowired
    ProductService productService;

    @Autowired
    ManufacturerService manufacturerService;

    @Autowired
    SellerService sellerService;

   public  List<ProductCategory> getProductCategories() throws ProductCategoryNotFoundException {
      return productCategoryRepo.findAll();
    }

    public ProductCategory getProductCategoryById(int productCategoryId )  throws ProductCategoryNotFoundException{
        Optional<ProductCategory> optionalProductCategory = productCategoryRepo.findById(productCategoryId);
        if(optionalProductCategory .isPresent())  {
            return optionalProductCategory .get();
        }
        else {
            throw new ProductCategoryNotFoundException();
        }
    }

    public List<ProductCategory> createProductCategory( List<ProductCategory> productCategoryList) throws ProductCategoryNotFoundException {

        for (ProductCategory productCategory : productCategoryList) {
            List<Product> products = productCategory.getProducts();
            products.forEach(product -> {
                product.setProductCategory(productCategory);
                List<Manufacturer> manufacturers = product.getManufacturers();
                manufacturers.forEach(manufacturer -> manufacturer.setProduct(product));
                product.setManufacturers(manufacturers);
                List<Seller> sellers = product.getSellers();
                sellers.forEach(seller -> seller.setProduct(product));
                product.setSellers(sellers);
            });


        productCategory.setProducts(products);

    }
            return  (List<ProductCategory>)productCategoryRepo.saveAll(productCategoryList);

    }


    /*public ProductCategory createProductCategory( ProductCategory productCategory) {

            List<Product> products = productCategory.getProducts();
            products.forEach(product -> {
                product.setProductCategory(productCategory);
                List<Manufacturer> manufacturers = product.getManufacturers();
                manufacturers.forEach(manufacturer -> manufacturer.setProduct(product));
                product.setManufacturers(manufacturers);
                List<Seller> sellers = product.getSellers();
                sellers.forEach(seller -> seller.setProduct(product));
                product.setSellers(sellers);
            });

            productCategory.setProducts(products);
            return productCategoryRepo.saveAndFlush(productCategory);

        }

     */
    public ProductCategory updateProductCategory( ProductCategory  productCategory) {
        List<Product> products = productCategory.getProducts();
        products.forEach(product -> {
            product.setProductCategory(productCategory);
            List<Manufacturer> manufacturers = product.getManufacturers();
            manufacturers.forEach(manufacturer -> manufacturer.setProduct(product));
            product.setManufacturers(manufacturers);
            List<Seller> sellers = product.getSellers();
            sellers.forEach(seller -> seller.setProduct(product));
            product.setSellers(sellers);
        });
        productCategory.setProducts(products);
        return  productCategoryRepo.saveAndFlush(productCategory);
    }

    public void deleteProductCategory( ProductCategory  productCategory){
        productCategoryRepo.delete(productCategory);
    }

}
