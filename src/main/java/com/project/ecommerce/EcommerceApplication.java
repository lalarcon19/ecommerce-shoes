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

}
