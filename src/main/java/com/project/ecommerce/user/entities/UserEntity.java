package com.project.ecommerce.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "USUARIO")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_USUARIO")
    private long idUser;

    @Column(name = "NOMBRE")
    private String name;

    @Column(name = "APELLIDO")
    private String lastName;

    @Column(name = "CORREO_ELECTRONICO")
    private String email;

    @Column(name = "CONTRASEÑA")
    private String contraseña;

    @Column(name = "TIPO_CEDULA")
    private String tipoCedula;

    @Column(name = "NUMERO_CEDULA")
    private String numeroCedula;

    @Column(name = "DIRECCION")
    private String direccion;
}
