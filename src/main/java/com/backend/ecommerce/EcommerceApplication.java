package com.backend.ecommerce;

import com.backend.ecommerce.entities.Category;
import com.backend.ecommerce.entities.Product;
import com.backend.ecommerce.services.interfaces.CategoryService;
import com.backend.ecommerce.services.interfaces.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.stream.Stream;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(CategoryService categoryService,
										ProductService productService){
		return args -> {
			// add category
			Stream.of("CATEGORY1","CATEGORY2","CATEGORY3").forEach(c->{
				Category category = Category.builder()
						.type(c)
						.build();
				categoryService.addCategory(category);
			});

			Stream.of("PRODUCT1","PRODUCT2","PRODUCT3","PRODUCT4").forEach(p->{
				Product product = Product.builder()
						.productName(p)
						.description(p+" descriptions")
						.productImageUrl("path_to"+p)
						.price(Math.random() * 100)
						.quantity((int) (Math.random() * 10))
						.category(categoryService.findCategoryById(1))
						.build();
				productService.addProduct(product);
			});
		};
	}

}
