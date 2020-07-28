package com.hcl.sample.service;

import com.hcl.sample.Repository.ManufacturerRepository;
import com.hcl.sample.model.Manufacturer;
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
public class ManufactureServiceTest {

    @InjectMocks
    ManufacturerService manufacturerService;

    @Mock
    ManufacturerRepository manufacturerRepository;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void getAllManufacturers(){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("India");
        manufacturer.setManufacturerRegion("Hyderabad");
        manufacturer.setManufacturingDate(LocalDate.of(2020,07,11));
        manufacturer.setProductCount(10);

        Manufacturer manufacturer1 = new Manufacturer();
        manufacturer1.setCountry("India");
        manufacturer1.setManufacturerRegion("Delhi");
        manufacturer1.setManufacturingDate(LocalDate.of(2020,07,18));
        manufacturer1.setProductCount(5);

        Mockito.when(manufacturerRepository.findAll()).thenReturn(Arrays.asList(manufacturer , manufacturer1));

        List<Manufacturer> responseList = manufacturerService.getAllManufacturers();

        Assert.assertNotNull(responseList);
        Assert.assertEquals(2,responseList.size());


    }

    @Test
    public void createManufacturer(){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("India");
        manufacturer.setManufacturerRegion("Hyderabad");
        manufacturer.setManufacturingDate(LocalDate.of(2020,07,11));
        manufacturer.setProductCount(10);

        Mockito.when(manufacturerRepository.save(Mockito.any(Manufacturer.class))).thenReturn(manufacturer);

        Manufacturer response = manufacturerService.createManufacturer(manufacturer);

        Assert.assertNotNull(response);
        Assert.assertEquals(response.getManufacturerCountry(), manufacturer.getManufacturerCountry());
        Assert.assertEquals(response.getManufacturerRegion(), manufacturer.getManufacturerRegion());
        Assert.assertEquals(response.getProductCount(), manufacturer.getProductCount());
    }

    @Test
    public void updateManufacturer(){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("India");
        manufacturer.setManufacturerRegion("Hyderabad");
        manufacturer.setManufacturingDate(LocalDate.of(2020,07,11));
        manufacturer.setProductCount(10);

        Mockito.when(manufacturerRepository.save(Mockito.any(Manufacturer.class))).thenReturn(manufacturer);

        Manufacturer response = manufacturerService.createManufacturer(manufacturer);

        response.setProductCount(15);

        Mockito.when(manufacturerRepository.save(Mockito.any(Manufacturer.class))).thenReturn(response);

        Manufacturer manufacturer1 = manufacturerService.updateManufacturer(response);

        Assert.assertNotNull(manufacturer1);
        Assert.assertEquals(manufacturer1.getManufacturerCountry(), response.getManufacturerCountry());
        Assert.assertEquals(manufacturer1.getManufacturerRegion(), response.getManufacturerRegion());
        Assert.assertEquals(manufacturer1.getProductCount(), response.getProductCount());

    }

    @Test
    public void deleteManufacturer(){
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setCountry("India");
        manufacturer.setManufacturerRegion("Hyderabad");
        manufacturer.setManufacturingDate(LocalDate.of(2020,07,11));
        manufacturer.setProductCount(10);

        Mockito.doNothing().when(manufacturerRepository).delete(Mockito.any(Manufacturer.class));

        manufacturerService.deleteManufacturer(manufacturer);

        Mockito.verify(manufacturerRepository, Mockito.times(1)).delete(manufacturer);
    }

    @Test
    public void getManufacturerById(){

        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setManufacturerId(1);
        manufacturer.setCountry("India");
        manufacturer.setManufacturerRegion("Hyderabad"); manufacturer.setManufacturingDate(LocalDate.of(2020,07,11));
        manufacturer.setProductCount(10);

        Mockito.when(manufacturerRepository.save(Mockito.any(Manufacturer.class))).thenReturn(manufacturer);


        Manufacturer response = manufacturerService.createManufacturer(manufacturer);
        Mockito.when(manufacturerRepository.findById(Mockito.any(Integer.class))).thenReturn(java.util.Optional.of(manufacturer));

        Manufacturer manufacturer1 = manufacturerService.getManufacturerById(1);

        Assert.assertNotNull(manufacturer1);
       // Assert.assertEquals(manufacturer1.getCountry(), manufacturer.getCountry());
        Assert.assertEquals(manufacturer1.getManufacturerRegion(), manufacturer.getManufacturerRegion());
        Assert.assertEquals(manufacturer1.getProductCount(), manufacturer.getProductCount());


    }

@Test
   public void getManufacturerByProductId(){
       Manufacturer manufacturer = new Manufacturer();
       manufacturer.setManufacturerId(2);
       manufacturer.setCountry("UK");
       manufacturer.setManufacturerRegion("London");
       manufacturer.setManufacturingDate(LocalDate.of(2020,07,25));
       manufacturer.setProductCount(5);
       Mockito.when(manufacturerRepository.findByProductProductId(Mockito.anyInt())).thenReturn(Arrays.asList( manufacturer));
       List<Manufacturer> response = manufacturerService.getManufacturerByProductId(1);
       Assert.assertNotNull(response);
       Assert.assertEquals(response.get(0).getManufacturerCountry(), manufacturer.getManufacturerCountry());
       Assert.assertEquals(response.get(0).getManufacturerRegion(), manufacturer.getManufacturerRegion());
       Assert.assertEquals(5, response.get(0).getProductCount());
   }
}
