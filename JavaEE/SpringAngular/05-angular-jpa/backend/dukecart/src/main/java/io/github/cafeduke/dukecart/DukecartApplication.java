package io.github.cafeduke.dukecart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cafeduke.dukecart.dao.ProductRepository;

@SpringBootApplication
public class DukecartApplication implements CommandLineRunner
{
    @Autowired
    private ProductRepository productRepository;
    
    public static void main(String[] args)
    {
        SpringApplication.run(DukecartApplication.class, args);
    }
    
    @Override
    public void run(String... arg) throws Exception 
    {
//        List<Product> listProduct = productRepository.findByCategoryWithQuery(2L);
//        System.out.println(listProduct.getFirst().getName());
    }
}
