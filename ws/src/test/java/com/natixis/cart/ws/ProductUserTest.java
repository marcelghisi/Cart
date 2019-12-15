package com.natixis.cart.ws;

import com.natixis.cart.ws.domain.Product;
import com.natixis.cart.ws.exception.ProductServiceException;
import com.natixis.cart.ws.repository.ProductRepository;
import com.natixis.cart.ws.services.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ProductUserTest {

    public static final String CAD_246_E_13_DE_11_EA_8_D_71_362_B_9_E_155667 = "3cad246e-13de-11ea-8d71-362b9e155667";

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void test_UserCreation_save_In_Mongo(){

        Product product = Product.builder().id("1111").name("IPhone").price(1.0).build();

        when(productRepository.save(product)).thenReturn(product);

       Product  newOne = productService.create(product);

        Assert.assertEquals(product.getId(),newOne.getId());

    }

    @Test
    public void test_UserUpdate_In_Mongo(){
        Product product = Product.builder().id("1111").name("IPhone").price(1.0).build();
        Product productNovo = Product.builder().id("1111").name("IPhone2").price(2.0).build();
        when(productRepository.save(product)).thenReturn(productNovo);
        Product  newOne = productService.update(product);
        Assert.assertEquals(productNovo.getName(),newOne.getName());
    }

    @Test
    public void test_UserDelete_In_Mongo(){
        try{
            Product product = Product.builder().id("1111").name("IPhone").price(1.0).build();
            doNothing().when(productRepository).delete(product);
            productService.delete(product.getId());
        } catch (Exception e){
            Assert.fail("Ola");
        }
    }

    @Test
    public void test_UserAll_In_Mongo(){
        Product product = Product.builder().id("1111").name("IPhone").price(1.0).build();
        when(this.productRepository.findAll()).thenReturn(Arrays.asList(product));
        List<Product> products = productService.findAll();
        Assert.assertEquals(1,products.size());
    }

    @Test(expected = ProductServiceException.class)
    public void test_FindOne_Exception() throws ProductServiceException {
        Optional<Product> product = Optional.of(Product.builder().id("1111").name("IPhone").price(1.0).build());
        when(this.productRepository.findById(any())).thenThrow(new ProductServiceException("aha"));
        Product productRet = this.productService.findById(any());
    }

    @Test
    public void test_FindOne() throws Exception {
        Optional<Product> product = Optional.of(Product.builder().id("1111").name("IPhone").price(1.0).build());
        when(this.productRepository.findById(anyString())).thenReturn(product);
        Product productRet = this.productService.findById(product.get().getId());
        Assert.assertEquals(productRet.getId(),"1111");
    }


}
