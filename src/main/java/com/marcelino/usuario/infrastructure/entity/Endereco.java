package com.marcelino.usuario.infrastructure.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "endereco")
public class Endereco {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "rua")
    private String rua;

    @Column(name = "numero")
    private long numero;

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "complemento", length = 25)
    private String complemento;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "estado")
    private String estado;

    @Column(name = "cep", length = 9)
    private String cep;
}
