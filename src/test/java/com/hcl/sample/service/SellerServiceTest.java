package com.hcl.sample.service;

import com.hcl.sample.Repository.SellerRepository;
import com.hcl.sample.model.Seller;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SellerServiceTest {

    @InjectMocks
    SellerService sellerService;

    @Mock
    SellerRepository sellerRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void getAllSellers(){
        Seller seller = new Seller();
        seller.setCountry("India");
        seller.setSellerRegion("Mumbai");
        seller.setSellDate(LocalDate.of(2020,07,15));
        seller.setProductSold(5);

        Seller seller1 = new Seller();
        seller1.setCountry("India");
        seller1.setSellerRegion("Bangalore");
        seller1.setSellDate(LocalDate.of(2020,07,20));
        seller1.setProductSold(6);

        Mockito.when(sellerRepository.findAll()).thenReturn(Arrays.asList(seller, seller1));

        List<Seller>  sellerList = sellerService.getAllSellers();

        Assert.assertNotNull(sellerList);
        Assert.assertEquals(2,sellerList.size() );

    }

    @Test
    public void createSeller(){
        Seller seller = new Seller();
        seller.setCountry("India");
        seller.setSellerRegion("Hyderabad");
        seller.setSellDate(LocalDate.of(2020,07,11));
        seller.setProductSold(10);

        Mockito.when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(seller);

        Seller response = sellerService.createSeller(seller);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getCountry(), seller.getCountry());
        Assert.assertEquals(response.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(response.getProductSold(), seller.getProductSold());
    }

    @Test
    public void updateSeller(){
        Seller seller = new Seller();
        seller.setCountry("India");
        seller.setSellerRegion("Hyderabad");
        seller.setSellDate(LocalDate.of(2020,07,11));
        seller.setProductSold(10);

        Mockito.when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(seller);

        Seller response = sellerService.updateSeller(seller);

        response.setProductSold(8);
        response.setCountry("USA");

        Mockito.when(sellerRepository.save(Mockito.any(Seller.class))).thenReturn(response);

        Seller seller1 = sellerService.updateSeller(response);

        Assert.assertNotNull(seller1);
        Assert.assertEquals(seller1.getCountry(), response.getCountry());
        Assert.assertEquals(seller1.getSellerRegion(), response.getSellerRegion());
        Assert.assertEquals(seller1.getProductSold(), response.getProductSold());

    }

    @Test
    public void deleteSeller(){
        Seller seller = new Seller();
        seller.setCountry("India");
        seller.setSellerRegion("Hyderabad");
        seller.setSellDate(LocalDate.of(2020,07,25));
        seller.setProductSold(10);

        Mockito.doNothing().when(sellerRepository).delete(Mockito.any(Seller.class));

        sellerService.deleteSeller(seller);

        Mockito.verify(sellerRepository, Mockito.times(1)).delete(seller);
    }

    @Test
    public void getSellerById(){

        Seller seller = new Seller();
        seller.setSellerId(1);
        seller.setCountry("India");
        seller.setSellerRegion("Hyderabad");
        seller.setSellDate(LocalDate.of(2020,07,11));
        seller.setProductSold(10);
        Mockito.when(sellerRepository.findById(Mockito.any(Integer.class))).thenReturn(java.util.Optional.of(seller));

        Seller seller1 = sellerService.getSellerById(1);

        Assert.assertNotNull(seller1);

        Assert.assertEquals(seller1.getSellerRegion(), seller.getSellerRegion());
        Assert.assertEquals(seller1.getProductSold(), seller.getProductSold());
        Assert.assertEquals(seller1.getSellDate(), seller.getSellDate());


    }

}
