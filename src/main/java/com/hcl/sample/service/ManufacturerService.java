package com.hcl.sample.service;

import com.hcl.sample.Repository.ManufacturerRepository;
import com.hcl.sample.model.Manufacturer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class ManufacturerService {

    @Autowired
    ManufacturerRepository  manufacturerRepository  ;


    public List<Manufacturer> getAllManufacturers(){
        return  (List<Manufacturer>)manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerById(int manufacturerId){
        Optional<Manufacturer> optionalManufacturer = manufacturerRepository.findById(manufacturerId);
        if(optionalManufacturer.isPresent()) {
            return optionalManufacturer.get();
        }
        else {
            return null;
        }
    }

    public Manufacturer createManufacturer( Manufacturer manufacturer){
        return  manufacturerRepository.save(manufacturer);
    }

    public Manufacturer updateManufacturer( Manufacturer manufacturer){
        return  manufacturerRepository.save(manufacturer);
    }


    public void deleteManufacturer( Manufacturer manufacturer){
        manufacturerRepository.delete(manufacturer);
    }


    public List<Manufacturer> getManufacturerByProductId(Integer productId){
      return   manufacturerRepository.findByProductProductId(productId);
    }

}
