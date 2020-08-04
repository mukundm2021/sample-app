package com.hcl.sample.controller;
import com.hcl.sample.model.Manufacturer;
import com.hcl.sample.model.Product;
import com.hcl.sample.model.ProductCategory;
import com.hcl.sample.model.Seller;
import com.hcl.sample.service.ProductCategoryService;
import com.hcl.sample.utility.Utils;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductCategoryController.class)
public class ProductCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductCategoryService productCatService;


    @Test
    public void addProductCategory() throws Exception {

        ProductCategory mockProductCat= new ProductCategory();
        mockProductCat.setProductCategoryId(1);;
        mockProductCat.setProductCategoryName("mobile");
        mockProductCat.setProductCategoryDescription("Mobile 2020");
        List<Product> products = getProducts();
        mockProductCat.setProducts(products);
        List<ProductCategory> productCatList = new ArrayList<ProductCategory>();
        productCatList.add(mockProductCat);
        //String inputJson = this.map2Json( mockProductCat);
        String inputJson = new Utils().map2Json(productCatList);

        Mockito.when(productCatService.createProductCategory(Mockito.anyList())).thenReturn(productCatList);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/productCategories")
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =result.getResponse();
        String outputJson = result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo(inputJson);
        assertEquals(HttpStatus.OK.value(),response.getStatus());
    }


    @Test
    public void getProductCategoryById() throws Exception {
        ProductCategory mockProductCategory= new ProductCategory();
        mockProductCategory.setProductCategoryId(2);;
        mockProductCategory.setProductCategoryName("Laptop");
        mockProductCategory.setProductCategoryDescription("Laptop 2020");
        List<Product> products = getProducts();
        mockProductCategory.setProducts(products);
        Mockito.when(productCatService.getProductCategoryById(Mockito.anyInt())).thenReturn(mockProductCategory);

        String URI = "/productCategories/1";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = new Utils().map2Json(mockProductCategory);
        String outputJson = result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo(expectedJson);
    }

    @Test
    public void getProductCategories() throws Exception {

        ProductCategory mockProductCategory1= new ProductCategory();
        mockProductCategory1.setProductCategoryId(1);;
        mockProductCategory1.setProductCategoryName("mobile");
        mockProductCategory1.setProductCategoryDescription(" Mobile 2020");
        List<Product> products = getProducts();
        mockProductCategory1.setProducts(products);
        ProductCategory mockProductCategory2= new ProductCategory();
        mockProductCategory2.setProductCategoryId(2);;
        mockProductCategory2.setProductCategoryName("Laptop");
        mockProductCategory2.setProductCategoryDescription("Laptop 2020");

        List<ProductCategory> prodCatList = new ArrayList<>();
        prodCatList.add(mockProductCategory1);
        prodCatList.add(mockProductCategory2);

        Mockito.when(productCatService.getProductCategories()).thenReturn(prodCatList);

        String URI = "/productCategories";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = new Utils().map2Json(prodCatList);
        String outputJson = result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo(expectedJson);
    }
        @Test
        public void updateProductCategory() throws Exception{

            ProductCategory mockProductCategory = new ProductCategory();
            mockProductCategory.setProductCategoryId(2);;
            mockProductCategory.setProductCategoryName("Laptop");
            mockProductCategory.setProductCategoryDescription("Mac 2020");
            String inputJson = new Utils().map2Json(mockProductCategory);
            Mockito.when(productCatService.getProductCategoryById(Mockito.anyInt())).thenReturn(mockProductCategory);
            Mockito.when(productCatService.updateProductCategory(Mockito.any())).thenReturn(mockProductCategory);

            RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/productCategories/2")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(inputJson).contentType(MediaType.APPLICATION_JSON);
            MvcResult result = mockMvc.perform(requestBuilder).andReturn();
            MockHttpServletResponse response =result.getResponse();
            String outputJson = result.getResponse().getContentAsString();
            assertThat(outputJson).isEqualTo(inputJson);
            assertEquals(HttpStatus.OK.value(),response.getStatus());
        }

    @Test
    public void deleteProductCategory() throws Exception{

        ProductCategory mockProductCategory = new ProductCategory();
        mockProductCategory.setProductCategoryId(3);;
        mockProductCategory.setProductCategoryName("Laptop");
        mockProductCategory.setProductCategoryDescription("NoteBook");
        String inputJson = new Utils().map2Json(mockProductCategory);
        Mockito.when(productCatService.getProductCategoryById(Mockito.anyInt())).thenReturn(mockProductCategory);
        Mockito.doNothing().when(productCatService).deleteProductCategory(Mockito.any());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/productCategories/3")
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =result.getResponse();
        String outputJson = result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo("Product category  deleted successfully");
        assertEquals(HttpStatus.OK.value(),response.getStatus());
        Mockito.verify(productCatService, Mockito.times(1)).deleteProductCategory(mockProductCategory);
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
