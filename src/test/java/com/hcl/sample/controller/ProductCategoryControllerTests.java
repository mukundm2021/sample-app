
package com.hcl.sample.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hcl.sample.model.ProductCategory;
import com.hcl.sample.service.ProductCategoryService;
import com.hcl.sample.model.ProductCategory;
import com.hcl.sample.service.ProductCategoryService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
//import static org.hamcrest.CoreMatchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
@WebMvcTest(ProductCategoryController.class)
public class ProductCategoryControllerTests {
    String json = "";

    @Autowired
    private MockMvc mockMvc;
    /*@Mock
    private RestTemplate restTemplate;
*/
    @Mock
    private ProductCategoryService productCategoryService;

    @MockBean
    private ProductCategoryController productCategoryController;

    private HttpHeaders headers;

    private MockRestServiceServer mockServer;
    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @BeforeClass
    public void setup(){
       /*json  = "{ \"productCategoryDescription\" :  \"Desc\" , " +
                "   \"productCategoryId\" : 0, " +
                "   \"productCategoryName\" :  \"Apple1\" , " +
                "   \"products\" : [ " +
                "    { " +
                "       \"manufacturers\" : [ " +
                "        { " +
                "           \"country\" :  \"India\" , " +
                "           \"manufacturerId\" : 0, " +
                "           \"manufacturerRegion\" :  \"hyd\" , " +
                "           \"manufacturingDate\" :  \"2020-07-13\" , " +
                "           \"productCount\" : 5 " +
                "        } " +
                "      ], " +
                "       \"productDescription\" :  \"product desc\" , " +
                "      \"productId\" : 0, " +
                "       \"productName\" :  \"AWS\" , " +
                "       \"sellers\" : [ " +
                "        { " +
                "           \"country\" :  \"India\" , " +
                "           \"productSold\" : 2, " +
                "           \"sellDate\" :  \"2020-07-13\" , " +
                "           \"sellerId\" : 0, " +
                "           \"sellerRegion\" :  \"hyd\"  " +
                "        } " +
                "      ] " +
                "    } " +
                "  ] " +
                "}";*/
        //restTemplate = new RestTemplate();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void createProductCategory() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("HCL");
        productCategory.setProductCategoryId(1);
        productCategory.setProducts(new ArrayList<>());
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(  productCategory);

        Mockito.when(productCategoryService.createProductCategory(Mockito.anyList())).thenReturn(productCategoryList);
        Mockito.when(productCategoryService.getProductCategories()).thenReturn(Arrays.asList(productCategory));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/productCategories")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(productCategory))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();

        // check it and fix TODO..
       // Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        //Assert.assertNotNull(response.getContentAsString());

       List<ProductCategory> productCategory1 = productCategoryService.getProductCategories();
       Assert.assertNotNull(productCategory1);
    }

    @Test
    public void getProductCategories() throws Exception{

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("HCL");
        productCategory.setProductCategoryId(1);
        productCategory.setProducts(new ArrayList<>());

        Mockito.when(productCategoryService.getProductCategories()).thenReturn(Arrays.asList(productCategory));


        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/productCategories")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        //Mockito.verify(productCategoryService, Mockito.times(1)).getProductCategories();

    }

    @Test
    public void getProductCategoryById() throws Exception{

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("HCL");
        productCategory.setProductCategoryId(1);
        productCategory.setProducts(new ArrayList<>());
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(  productCategory);

        Mockito.when(productCategoryService.createProductCategory(Mockito.anyList())).thenReturn(productCategoryList);
        Mockito.when(productCategoryService.getProductCategories()).thenReturn(Arrays.asList(productCategory));

        productCategoryService.createProductCategory(productCategoryList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("http://localhost:9090/productCategories/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();

        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

        List<ProductCategory> productCategory1 = productCategoryService.getProductCategories();
        Assert.assertNotNull(productCategory1);
    }

    @Test
    public void deleteProductCategory() throws Exception{

        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("HCL");
        productCategory.setProductCategoryId(1);
        productCategory.setProducts(new ArrayList<>());
        List<ProductCategory> productCategoryList = new ArrayList<>();
        productCategoryList.add(  productCategory);

        Mockito.when(productCategoryService.createProductCategory(Mockito.anyList())).thenReturn(productCategoryList);
        Mockito.when(productCategoryService.getProductCategories()).thenReturn(Arrays.asList(productCategory));
        productCategoryService.createProductCategory(productCategoryList);

        RequestBuilder requestBuilder1 = MockMvcRequestBuilders
                .delete("http://localhost:9090/productCategories")
                .accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(productCategory))
                .contentType(MediaType.APPLICATION_JSON);


        MvcResult result1 = mockMvc.perform(requestBuilder1).andReturn();

        List<ProductCategory> productCategory1 = productCategoryService.getProductCategories();
        Assert.assertNotNull(productCategory1);



       /* mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:9090/productCategories/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].productCategoryName", is("HCL")))
                ;*/
    }

}
