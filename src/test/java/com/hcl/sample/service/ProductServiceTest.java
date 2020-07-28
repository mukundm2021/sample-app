package com.hcl.sample.service;

import com.hcl.sample.Repository.ProductRepository;
import com.hcl.sample.model.Manufacturer;
import com.hcl.sample.model.Product;
import com.hcl.sample.model.Seller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void createProduct(){
        Product product= new Product();
        product.setProductName("Java");
        Seller seller = new Seller();
        seller.setProductSold(10);
        product.setSellers(Arrays.asList(seller));
        product.setManufacturers(new ArrayList<>());

        Mockito.when(productRepository.saveAndFlush(Mockito.any(Product.class))).thenReturn(product);

        Product product1 = productService.createProduct(product);
        Assert.assertNotNull(product1);
        Assert.assertEquals(product1.getProductName(), product.getProductName());
    }

    @Test
    public void deleteProduct(){
        Product product= new Product();
        product.setProductName("Mobile");
        Seller seller = new Seller();
        seller.setProductSold(10);
        product.setSellers(Arrays.asList(seller));
        product.setManufacturers(new ArrayList<>());
        Mockito.doNothing().when(productRepository).delete(Mockito.any(Product.class));
        productService.deleteProduct(product);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
    }
    @Test
   public void  getAllProducts(){
        Product product= new Product();
        product.setProductName("Mac 2020");
        Seller seller = new Seller();
        seller.setSellerId(2);
        seller.setProductSold(4);
        product.setSellers(Arrays.asList(seller));
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(2);
        manufacturer.setCountry("India");
        product.setManufacturers(Arrays.asList(manufacturer));

        Product product1= new Product();
        product1.setProductName("Apple I Phone ");
        Seller seller1 = new Seller();
        seller1.setSellerId(3);
        seller1.setProductSold(5);
        product1.setSellers(Arrays.asList(seller1));
        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setManufacturerId(2);
        manufacturer1.setCountry("India");

        product1.setManufacturers(Arrays.asList(manufacturer1));
        product1.setSellers(Arrays.asList(seller1));
        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);
        Mockito.when(productRepository.findAll()).thenReturn(products);
        assertEquals(2, productService.getAllProducts().size());

    }
    @Test
    public void getProductById(){
        Product product= new Product();
        product.setProductName("IPhone 6");
        Seller seller = new Seller();
        seller.setSellerId(4);
        seller.setSellerRegion("California");
        seller.setCountry("USA");
        seller.setProductSold(8);
        seller.setSellDate(LocalDate.of(2020,07,26));
        product.setSellers(Arrays.asList(seller));
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("USA");
        manufacturer.setProductCount(15);
        manufacturer.setManufacturingDate(LocalDate.of(2020,07,20));

        manufacturer.setManufacturerId(5);
        product.setManufacturers(Arrays.asList(manufacturer));

        Mockito.when(productRepository.findById(Mockito.any(Integer.class))).thenReturn(java.util.Optional.of(product));

        Product  actualProduct  = productService.getProductById(1);

        Assert.assertNotNull(actualProduct);

        Assert.assertEquals(actualProduct.getProductName(), product.getProductName());
        Assert.assertEquals(actualProduct.getProductId(), product.getProductId());

    }
    @Test
    public void getProductByProductCategoryId(){
        Product product= new Product();
        product.setProductName("IPhone 6");
        Seller seller = new Seller();
        seller.setSellerId(4);
        seller.setProductSold(6);
        product.setSellers(Arrays.asList(seller));
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(1);
        manufacturer.setCountry("India");
        Mockito.when(productRepository.findByProductCategoryProductCategoryId(Mockito.anyInt())).thenReturn(Arrays.asList(product));

        List<Product>  responseList  = productService.getProductByProductCategoryId(1);

        Assert.assertNotNull( responseList);

        Assert.assertEquals(1, responseList.size());
        Assert.assertEquals(product.getProductName(), responseList.get(0).getProductName());
    }
}
