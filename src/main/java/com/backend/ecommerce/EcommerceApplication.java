package com.backend.ecommerce;

import com.backend.ecommerce.entities.*;
import com.backend.ecommerce.securities.config.RsaKeyConfig;
import com.backend.ecommerce.services.interfaces.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyConfig.class)
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

			// save role
			AppRole adminRole = AppRole.builder()
					.roleName("ADMIN")
					.build();
			adminRole = appRoleService.saveRole(adminRole);

			AppRole userRole = AppRole.builder()
					.roleName("USER")
					.build();
			userRole = appRoleService.saveRole(userRole);

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
					.phoneNumber("0321590833")
					.appRoles(Stream.of((Math.random() > 0.5) ? finalAdminRole : finalUserRole).collect(Collectors.toSet()))
					.build()).forEach(appUserService::addUser);

			AppUser appUser = appUserService.findUserByUsernameContains("user1").get(0);
			Stream.of("PRODUCT1","PRODUCT2","PRODUCT3","PRODUCT4").forEach(p->{
				Product product = Product.builder()
						.productName(p)
						.description(p+" descriptions")
						.productImageUrl("path_to"+p)
						.price((Math.random()+1) * 100)
						.stockQuantity((int) ((Math.random()+1) * 10))
						.category(categoryService.findCategoryById(1))
						.appUser(appUser)
						.build();
				productService.addProduct(product);
			});

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
			command.setAppUser(appUser);

			commandService.addCommand(command);

		};
	}

}
