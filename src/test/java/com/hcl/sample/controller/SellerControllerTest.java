package com.hcl.sample.controller;

import com.hcl.sample.model.Seller;
import com.hcl.sample.service.ProductService;
import com.hcl.sample.service.SellerService;
import com.hcl.sample.utility.Utils;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(SpringRunner.class)
@WebMvcTest(value = SellerController.class)
public class SellerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SellerService sellerService;

    @MockBean
    ProductService productService;

    @Test
    public void getAllSellers() throws Exception {
        List<Seller> sellers = getSellerList();
        Mockito.when(sellerService.getAllSellers()).thenReturn(sellers);
        String URI = "/sellers";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = new Utils().map2Json( sellers);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @Test
    public void getSellersByProductId() throws Exception {
        List<Seller> sellers = getSellerList();
        Mockito.when(sellerService.getSellerByProductId(Mockito.anyInt())).thenReturn(sellers);
        String URI = "/products/1/sellers";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = new Utils().map2Json( sellers);
        String outputInJson = result.getResponse().getContentAsString();
        assertThat(outputInJson).isEqualTo(expectedJson);
    }

    @Test
    public void getSellerById() throws Exception {
        Seller mockedSeller = getMockedSeller();
        Mockito.when(sellerService.getSellerById(Mockito.anyInt())).thenReturn(mockedSeller);

        String URI = "/sellers/10";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                URI).accept(
                MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        String expectedJson = new Utils().map2Json(mockedSeller);
        String outputJson = result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo(expectedJson);
    }

    @Test
    public void addNewSellerForProductId() throws Exception {

        Seller mockedSeller = getMockedSeller();
        String inputJson = new Utils().map2Json(mockedSeller);

        Mockito.when(sellerService.createSeller(Mockito.any())).thenReturn(mockedSeller);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products/1/sellers")

                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =result.getResponse();
        String outputJson = result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo(inputJson);
        assertEquals(HttpStatus.CREATED.value(),response.getStatus());
    }


    @Test
    public void updateSeller()  throws Exception{
        Seller mockedSeller = getMockedSeller();
        String inputJson = new Utils().map2Json(mockedSeller);
        Mockito.when(sellerService.getSellerById(Mockito.anyInt())).thenReturn(mockedSeller);
        Mockito.when(sellerService.updateSeller(Mockito.any(Seller.class))).thenReturn(mockedSeller);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/sellers/10")

                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =result.getResponse();
        String outputJson = result.getResponse().getContentAsString();
        assertThat(outputJson).isEqualTo(inputJson);
        assertEquals(HttpStatus.OK.value(),response.getStatus());

    }
    @Test
    public void deleteSeller() throws Exception{
        Seller mockedSeller = getMockedSeller();
        String inputJson = new Utils().map2Json(mockedSeller);
        Mockito.when(sellerService.getSellerById(Mockito.anyInt())).thenReturn(mockedSeller);
        Mockito.doNothing().when(sellerService).deleteSeller(Mockito.any(Seller.class));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/sellers/10")
                .accept(MediaType.APPLICATION_JSON)
                .content(inputJson).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response =result.getResponse();
        String outputJson = result.getResponse().getContentAsString();
        assertNotNull(outputJson);
     }
    public List<Seller> getSellerList(){

        Seller seller = new Seller();
        seller.setCountry("india");
        seller.setSellerRegion("Delhi");
        seller.setSellerId(5);
        seller.setProductSold(5);
       // seller.setSellDate(LocalDate.of(2020,07,28));
        Seller seller1 = new Seller();
        seller1.setCountry("india");
        seller1.setSellerRegion("Hyderabad");
        seller1.setSellerId(6);
        seller1.setProductSold(6);
        //seller1.setSellDate(LocalDate.of(2020,07,28));
        List<Seller> sellers = new ArrayList<>();
        sellers.add(seller);
        sellers.add(seller1);
        return sellers;
    }

    public Seller getMockedSeller(){
        Seller seller = new Seller();
        seller.setCountry("india");
        seller.setSellerRegion("Hyderabad");
        seller.setSellerId(10);
        seller.setProductSold(5);
       // seller.setSellDate(LocalDate.of(2020,07,30));
        return seller ;
    }
}
