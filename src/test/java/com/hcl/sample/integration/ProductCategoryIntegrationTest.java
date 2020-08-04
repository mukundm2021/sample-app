 package com.hcl.sample.integration;
 import com.hcl.sample.SampleAppApplication;
 import com.hcl.sample.model.Manufacturer;
 import com.hcl.sample.model.Product;
 import com.hcl.sample.model.ProductCategory;
 import com.hcl.sample.model.Seller;
 import com.hcl.sample.utility.Utils;
 import org.junit.Assert;
 import org.springframework.boot.web.server.LocalServerPort;
 import static org.assertj.core.api.Assertions.assertThat;
 import java.util.ArrayList;
 import java.util.Arrays;
 import java.util.List;
 import org.junit.Test;
 import org.junit.runner.RunWith;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.boot.test.context.SpringBootTest;
 import org.springframework.boot.test.web.client.TestRestTemplate;
 import org.springframework.http.HttpEntity;
 import org.springframework.http.HttpHeaders;
 import org.springframework.http.HttpMethod;
 import org.springframework.http.ResponseEntity;
 import org.springframework.test.context.junit4.SpringRunner;


 @RunWith(SpringRunner.class)
 @SpringBootTest(classes = SampleAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
 public class ProductCategoryIntegrationTest {

     @LocalServerPort
     private int port;

     @Autowired
     private TestRestTemplate testRestTemplate;
     private HttpHeaders headers = new HttpHeaders();

     @Test
     public void addProductCategory() throws Exception {
         ProductCategory productCat = new ProductCategory();
         productCat.setProductCategoryId(1001);

         productCat.setProductCategoryName("mobile");
         productCat.setProductCategoryDescription("Mobile 2020");
         List<Product> products = getProducts();
         productCat.setProducts(products);
         List<ProductCategory> productCatList = new ArrayList<ProductCategory>();
         productCatList.add(productCat);
         String uriToCreateProductCategories = "/productCategories";

         String inputJson = new Utils().map2Json(productCatList);

         HttpEntity<List<ProductCategory>> entity = new HttpEntity<List<ProductCategory>>(productCatList, headers);
         ResponseEntity<List> response = testRestTemplate.exchange(
                 formFullURLWithPort(uriToCreateProductCategories),
                 HttpMethod.POST, entity, List.class);

         String responseJson = new Utils().map2Json(response.getBody());
         assertThat(responseJson).isEqualTo(inputJson);
     }

     @Test
     public void getProductCategoryById() throws Exception {

         ProductCategory productCategory = new ProductCategory();
         productCategory.setProductCategoryId(1002);
         productCategory.setProductCategoryName("Laptop");
         productCategory.setProductCategoryDescription("Laptop 2020");
         String inputJson = new Utils().map2Json(productCategory);
         String uriToCreateProductCategories = "/productCategories";
         HttpEntity<List<ProductCategory>> entity = new HttpEntity<List<ProductCategory>>(Arrays.asList(productCategory), headers);
         testRestTemplate.exchange(formFullURLWithPort(uriToCreateProductCategories),
                 HttpMethod.POST, entity, List.class);
         String URI = "/productCategories/1002";
         String bodyJsonResponse = testRestTemplate.getForObject(formFullURLWithPort(URI), String.class);
         assertThat(bodyJsonResponse).isEqualTo(inputJson);
     }
     @Test
     public void getProductCategories() throws Exception {
         ProductCategory productCategory1= new ProductCategory();
         productCategory1.setProductCategoryId(1001);;
         productCategory1.setProductCategoryName("mobile");
         productCategory1.setProductCategoryDescription("Mobile 2020");
         List<Product> products = getProducts();
         productCategory1.setProducts(products);
         ProductCategory productCategory2= new ProductCategory();
         productCategory2.setProductCategoryId(1002);;
         productCategory2.setProductCategoryName("Laptop");
         productCategory2.setProductCategoryDescription("Laptop 2020");
         String productCatJson = new Utils().map2Json(Arrays.asList(productCategory1,productCategory2));
         String uriToCreateProductCategories = "/productCategories";
         HttpEntity<List<ProductCategory>> entity = new HttpEntity<List<ProductCategory>>(Arrays.asList(productCategory1,productCategory2), headers);
         testRestTemplate.exchange(formFullURLWithPort(uriToCreateProductCategories),
                 HttpMethod.POST, entity, List.class);
         String URI = "/productCategories";
         List response = testRestTemplate.getForObject(formFullURLWithPort(URI), List.class);
         String responseJsonString = new Utils().map2Json(response);
         assertThat(responseJsonString).isEqualTo(productCatJson);

     }

     @Test
     public void deleteProductCategory() throws Exception {
         ProductCategory productCategory = new ProductCategory();
         productCategory.setProductCategoryId(1003);;
         productCategory.setProductCategoryName("Laptop");
         productCategory.setProductCategoryDescription("NoteBook");
         String inputJson = new Utils().map2Json(Arrays.asList(productCategory));
         String uriToCreateProductCategories = "/productCategories";
         HttpEntity<List<ProductCategory>> entity = new HttpEntity<List<ProductCategory>>(Arrays.asList(productCategory), headers);
         testRestTemplate.exchange(formFullURLWithPort(uriToCreateProductCategories),
                 HttpMethod.POST, entity, List.class);
         String URI = "/productCategories/1003";
         testRestTemplate.delete(formFullURLWithPort(URI), String.class);
         String bodyJsonResponse = testRestTemplate.getForObject(formFullURLWithPort(URI), String.class);
         Assert.assertNull(bodyJsonResponse);

     }

     @Test
     public void updateProductCategory() throws Exception {
         ProductCategory productCategory = new ProductCategory();
         productCategory.setProductCategoryId(1002);;
         productCategory.setProductCategoryName("Laptop");
         productCategory.setProductCategoryDescription("NoteBook");
         String inputJson = new Utils().map2Json(Arrays.asList(productCategory));
         String uriToCreateProductCategories = "/productCategories";
         HttpEntity<List<ProductCategory>> entity = new HttpEntity<List<ProductCategory>>(Arrays.asList(productCategory), headers);
         testRestTemplate.exchange(formFullURLWithPort(uriToCreateProductCategories),
                 HttpMethod.POST, entity, List.class);
         String URI = "/productCategories/1002";
         productCategory.setProductCategoryName("MAC");
         productCategory.setProductCategoryDescription("MAC NoteBook");
         testRestTemplate.put(formFullURLWithPort(URI), productCategory);
         String updatedProductCategoryJson = new Utils().map2Json(productCategory);
         String jsonResponse = testRestTemplate.getForObject(formFullURLWithPort(URI), String.class);
         assertThat(jsonResponse).isEqualTo(updatedProductCategoryJson);

     }

     /**
      * This utility method to create the url for given uri. It appends the RANDOM_PORT generated port
      */
     private String formFullURLWithPort(String uri) {
         return "http://localhost:" + port + uri;
     }

    public List<Product>  getProducts(){
        Product mockProduct1 = new Product();
        mockProduct1.setProductId(1004);
        mockProduct1.setProductName("Mac Book");
        mockProduct1.setProductDescription(" this is  very fast mac book series  ");
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("india");
        manufacturer.setManufacturerRegion("Hyderabad");
        manufacturer.setManufacturerId(3001);
        manufacturer.setProductCount(5000);
        // manufacturer.setManufacturingDate(LocalDate.of(2020,07,22));
        List<Manufacturer> manufacturers = new ArrayList<>();
        manufacturers.add(manufacturer);
        mockProduct1.setManufacturers(manufacturers);
        Seller seller = new Seller();
        seller.setCountry("india");
        seller.setSellerRegion("Delhi");
        seller.setSellerId(4001);
        seller.setProductSold(2000);
        // seller.setSellDate(LocalDate.of(2020,07,28));
        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller);

        mockProduct1.setSellers(sellers);

        Product mockProduct2 = new Product();
        mockProduct2.setProductId(2006);
        mockProduct2.setProductName("Mac Pro Book");
        mockProduct2.setProductDescription(" this is super hit Pro book   ");
        Manufacturer manufacturer2 = new Manufacturer();
        manufacturer2.setCountry("india");
        manufacturer2.setManufacturerRegion("Hyderabad");
        manufacturer2.setManufacturerId(3005);
        manufacturer2.setProductCount(1000);
        // manufacturer.setManufacturingDate(LocalDate.of(2020,07,22));
        List<Manufacturer> manufacturers2 = new ArrayList<>();
        manufacturers2.add(manufacturer2);
        mockProduct2.setManufacturers(manufacturers2);
        Seller seller2 = new Seller();
        seller2.setCountry("india");
        seller2.setSellerRegion("kolkata");
        seller2.setSellerId(4003);
        seller2.setProductSold(1003);
        // seller.setSellDate(LocalDate.of(2020,07,28));
        List<Seller> sellers2 = new ArrayList<>();
        sellers2.add(seller2);

        mockProduct1.setSellers(sellers2);

        List<Product> productList = new ArrayList<>();
        productList.add(mockProduct1);
        productList.add(mockProduct2);
        return productList;
    }
 }
