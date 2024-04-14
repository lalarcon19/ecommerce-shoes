package com.project.ecommerce;

import com.project.ecommerce.user.respository.IUserRepository;
import com.project.ecommerce.user.util.RoleEnum;
import com.project.ecommerce.user.entity.PermissionEntity;
import com.project.ecommerce.user.entity.RoleEntity;
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
	CommandLineRunner runner(IUserRepository userRepository) {
		return args -> {
			PermissionEntity createPermission = PermissionEntity.builder()
					.name("CREATE")
					.build();
			PermissionEntity readPermission = PermissionEntity.builder()
					.name("READ")
					.build();
			PermissionEntity updatePermission = PermissionEntity.builder()
					.name("UPDATE")
					.build();
			PermissionEntity deletePermission = PermissionEntity.builder()
					.name("DELETE")
					.build();

			RoleEntity roleAdmin = RoleEntity.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionEntities(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			RoleEntity roleUser = RoleEntity.builder()
					.roleEnum(RoleEnum.USER)
					.permissionEntities(Set.of(readPermission))
					.build();

			UserEntity userPruebas = UserEntity.builder()
					.username("admin")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpire(true)
					.roles(Set.of(roleAdmin))
					.build();

			UserEntity userPruebas1 = UserEntity.builder()
					.username("user")
					.password(new BCryptPasswordEncoder().encode("1234"))
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpire(true)
					.roles(Set.of(roleUser))
					.build();

			userRepository.saveAll(List.of(userPruebas, userPruebas1));

		};
	}



}
