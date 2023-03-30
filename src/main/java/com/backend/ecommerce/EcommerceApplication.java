package com.backend.ecommerce;

import com.backend.ecommerce.entities.*;
import com.backend.ecommerce.services.interfaces.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}


	@Bean
	CommandLineRunner commandLineRunner(CategoryService categoryService,
										ProductService productService,
										CommandService commandService,
										AppUserService appUserService,
										AppRoleService appRoleService){
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
						.stockQuantity((int) (Math.random() * 10))
						.category(categoryService.findCategoryById(1))
						.build();
				productService.addProduct(product);
			});

			// save role
			AppRole adminRole = AppRole.builder()
					.roleName("ADMIN")
					.build();
			adminRole = appRoleService.addRole(adminRole);

			AppRole userRole = AppRole.builder()
					.roleName("USER")
					.build();
			userRole = appRoleService.addRole(userRole);

			AppRole finalAdminRole = adminRole;
			AppRole finalUserRole = userRole;

			Stream.of("user1", "user2", "user3").map(username -> AppUser.builder()
					.username(username)
					.password("1234")
					.address(username + " address")
					.email(username + "@gmail.com")
					.firstName(username + " firstname")
					.lastName(username + " lastname")
					.profilePictureUrl(username + " profile picture url")
					.phoneNumber("032 00 000 01")
					.appRoles(Arrays.asList((Math.random() > 0.5) ? finalAdminRole : finalUserRole))
					.build()).forEach(appUserService::addUser);
			Command command = new Command();

			List<CommandItem> commandItems = new ArrayList<>();

			CommandItem commandItem1 = new CommandItem();
			commandItem1.setProduct(productService.findProductById(1L));
			commandItem1.setQuantity(1);

			CommandItem commandItem2 = new CommandItem();
			commandItem2.setProduct(productService.findProductById(3L));
			commandItem2.setQuantity(1);

			commandItems.add(commandItem1);
			commandItems.add(commandItem2);

			command.setCommandItems(commandItems);

			commandService.addCommand(command);

		};
	}

}
