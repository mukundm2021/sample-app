package com.hcl.sample.Repository;

import com.hcl.sample.model.Manufacturer;
import com.hcl.sample.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ManufacturerRepository extends CrudRepository <Manufacturer, Integer>{
    List<Manufacturer> findByProductProductId(Integer  productId);
    List<Manufacturer> findByManufacturerRegion(String manufacturerRegion);
    List<Manufacturer> findByManufacturingDate(LocalDate manufacturingDate);
    List<Manufacturer> findByManufacturerCountry (String manufacturerCountry);
    List<Manufacturer> findByManufacturingDateAndManufacturerCountryAndManufacturerRegion (LocalDate manufacturingDate, String country, String region);
    List<Manufacturer> findByManufacturingDateOrManufacturerCountryOrManufacturerRegion (LocalDate manufacturingDate, String country, String region);



}
