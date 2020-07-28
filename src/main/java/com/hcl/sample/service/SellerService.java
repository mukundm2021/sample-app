package com.hcl.sample.service;


import com.hcl.sample.Repository.SellerRepository;
import com.hcl.sample.model.Seller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class SellerService {
    @Autowired
    SellerRepository sellerRepository ;


    public List<Seller> getAllSellers(){
       return  (List<Seller>)sellerRepository.findAll();
    }

    public Seller getSellerById(int sellerId){
        Optional<Seller> optionalSeller = sellerRepository.findById(sellerId);
        if(optionalSeller.isPresent()) {
            return optionalSeller.get();
        }
        else {
            return null;
        }
    }

    public List<Seller> getSellerByProductId(int productId){
       return sellerRepository.findByProductProductId(productId);
    }

    public Seller createSeller( Seller seller){
        return  sellerRepository.save(seller);
    }

    public  Seller updateSeller( Seller seller){
        return  sellerRepository.save(seller);
    }


    public void deleteSeller( Seller seller){
        sellerRepository.delete(seller);
     }

}
