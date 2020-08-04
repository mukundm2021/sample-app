package com.hcl.sample.service;
import com.hcl.sample.Repository.ProductCategoryRepository;
import com.hcl.sample.model.ProductCategory;
import com.hcl.sample.utility.ProductCategoryNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.Mockito.times;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryServiceTest {

    @Autowired
    ProductCategoryService productCategoryService;
    @MockBean
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void getProductCategories() throws ProductCategoryNotFoundException {
        Mockito.when(productCategoryRepository.findAll()).thenReturn(getProductCategoryList());
        assertEquals(2, productCategoryService.getProductCategories().size());
    }

    @Test
    public void getProductCategoryById() throws ProductCategoryNotFoundException {
        int productCategoryId = 1;
        Mockito.when(productCategoryRepository.findById(Mockito.anyInt())).thenReturn(getProductCategory(productCategoryId));
        assertEquals(productCategoryId, productCategoryService.getProductCategoryById(Mockito.anyInt()).getProductCategoryId());
        assertEquals("mobile", productCategoryService.getProductCategoryById(Mockito.anyInt()).getProductCategoryName());

    }

    @Test
    public void createProductCategory() throws ProductCategoryNotFoundException {
       List<ProductCategory> productCategoryList = getProductCategoryList();
        Mockito.when(productCategoryRepository.saveAll(Mockito.anyList())).thenReturn(productCategoryList);
        List<ProductCategory> productCategoryListCreated = productCategoryService.createProductCategory(productCategoryList);
        verify(productCategoryRepository, times(1)).saveAll(productCategoryList);
        assertEquals(1, productCategoryListCreated.get(0).getProductCategoryId());
        assertEquals("mobile", productCategoryListCreated.get(0).getProductCategoryName());

    }

    @Test
    public void deleteProductCategory() {
        ProductCategory productCategory = getProductCategory();
        productCategoryService.deleteProductCategory(productCategory);
        verify(productCategoryRepository, times(1)).delete(productCategory);

    }

    @Test
    public void updateProductCategory() {
        ProductCategory productCategory = getProductCategory();
        Mockito.when(productCategoryRepository.saveAndFlush(Mockito.any(ProductCategory.class))).thenReturn(productCategory);
        ProductCategory productCategoryUpdated = productCategoryService.updateProductCategory(productCategory);
        verify(productCategoryRepository, times(1)).saveAndFlush(productCategory);
        assertEquals(1, productCategoryUpdated.getProductCategoryId());
        assertEquals("mobile", productCategoryUpdated.getProductCategoryName());


    }

    public List<ProductCategory> getProductCategoryList() {
        List<ProductCategory> productCatList = new ArrayList<ProductCategory>();
        ProductCategory productCat1 = new ProductCategory();
        productCat1.setProductCategoryId(1);
        productCat1.setProductCategoryName("mobile");
        productCat1.setProductCategoryDescription("mobile phone desc");
        productCatList.add(productCat1);
        ProductCategory productCat2 = new ProductCategory();
        productCat2.setProductCategoryId(2);
        productCat2.setProductCategoryName("Laptop");
        productCat2.setProductCategoryDescription("Laptop  desc");
        productCatList.add(productCat2);
        return productCatList;
    }
    public Optional<ProductCategory> getProductCategory(int productCategoryId) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(productCategoryId);
        productCategory.setProductCategoryName("mobile");
        productCategory.setProductCategoryDescription("mobile phone desc");

        Optional<ProductCategory> productOptional = Optional.of(productCategory);
        return productOptional;

    }

    public ProductCategory getProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryId(1);
        productCategory.setProductCategoryName("mobile");
        productCategory.setProductCategoryDescription("mobile phone desc");
        return productCategory;
    }
}
