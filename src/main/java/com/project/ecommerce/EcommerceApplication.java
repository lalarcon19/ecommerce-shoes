package com.project.ecommerce;

import com.project.ecommerce.category.entity.Category;
import com.project.ecommerce.category.repository.CategoryRepository;
import com.project.ecommerce.user.util.DocumentType;
import com.project.ecommerce.utils.enums.CategoryEnum;
import com.project.ecommerce.product.entity.Product;
import com.project.ecommerce.product.respository.ProductRepository;
import com.project.ecommerce.user.respository.UserRepository;
import com.project.ecommerce.utils.enums.RoleEnum;
import com.project.ecommerce.user.entity.Permission;
import com.project.ecommerce.user.entity.Role;
import com.project.ecommerce.user.entity.UserEntity;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Set;

@SpringBootApplication
public class EcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(UserRepository userRepository, ProductRepository productRepository, CategoryRepository categoryRepository) {
		return args -> {
			Permission createPermission = Permission.builder()
					.name("CREATE")
					.build();
			Permission readPermission = Permission.builder()
					.name("READ")
					.build();
			Permission updatePermission = Permission.builder()
					.name("UPDATE")
					.build();
			Permission deletePermission = Permission.builder()
					.name("DELETE")
					.build();

			Role roleAdmin = Role.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionEntities(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			Role roleUser = Role.builder()
					.roleEnum(RoleEnum.USER)
					.permissionEntities(Set.of(readPermission))
					.build();

			UserEntity userPruebas = UserEntity.builder()
					.name("admin")
					.lastName("")
					.document("123456789")
					.documentType(DocumentType.CC)
					.address("calle 100")
					.username("admin")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpire(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userPruebas1 = UserEntity.builder()
					.name("user")
					.lastName("")
					.document("123456888")
					.documentType(DocumentType.CE)
					.address("calle 100")
					.username("user")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpire(true)
					.roles(Set.of(roleUser))
					.build();

			Category category1 = Category.builder()
					.name(CategoryEnum.DEPORTIVOS)
					.description("DEPORTIVOS")
					.build();

			Category category2 = Category.builder()
					.name(CategoryEnum.CASUALES)
					.description("CASUALES")
					.build();

			Category category3 = Category.builder()
					.name(CategoryEnum.ELEGANTES)
					.description("ELEGANTES")
					.build();


			Product product1 = Product.builder()
					.name("product1")
					.price(1000)
					.category(category1)
					.build();

			Product product2 = Product.builder()
					.name("product2")
					.price(1000)
					.category(category2)
					.build();

			Product product3 = Product.builder()
					.name("product3")
					.price(1000)
					.category(category3)
					.build();



			categoryRepository.saveAllAndFlush(List.of(category1, category2, category3));
			productRepository.saveAllAndFlush(List.of(product1, product2, product3));
			userRepository.saveAll(List.of(userPruebas, userPruebas1));

		};
	}



}
