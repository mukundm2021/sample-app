package com.hcl.sample.service;
import java.util.List;
import java.util.Optional;
import com.hcl.sample.Repository.ProductRepository;
import com.hcl.sample.model.Manufacturer;
import com.hcl.sample.model.Product;
import com.hcl.sample.model.Seller;
import com.hcl.sample.utility.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository ;


    public List<Product> getAllProducts(){
       return productRepository.findAll();

    }

    public Product getProductById(int productId){
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if(optionalProduct.isPresent()) {
            return optionalProduct.get();
        }
        else {
            return null;
        }
    }


    public List<Product> getProductByProductCategoryId(int productCategoryId) {
      return  productRepository.findByProductCategoryProductCategoryId(productCategoryId);

    }

      public Product createProduct( Product product){
          List<Manufacturer> manufacturers = product.getManufacturers();
          manufacturers.forEach(manufacturer -> manufacturer.setProduct(product));
          product.setManufacturers(manufacturers);
          List<Seller> sellers = product.getSellers();
          sellers.forEach(seller -> seller.setProduct(product));
          product.setSellers(sellers);
        return  productRepository.saveAndFlush(product);
    }

    public Product updateProduct( Product product){
        return  productRepository.save(product);
    }


    public void deleteProduct( Product product){
         productRepository.delete(product);
     }

}
