package com.hcl.sample.controller;
import com.hcl.sample.model.ProductCategory;
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
        mockProductCat.setProductCategoryDescription(" Mobile 2020");
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

        //String expectedJson = this.map2Json(prodCatList);
        String expectedJson = new Utils().map2Json(prodCatList);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);
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


}
