package com.aula.projeto.user;

import java.util.UUID;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //gera getters e setters
@Entity(name = "tb_user") //gera banco de dados com nome
public class UserModel {

    public UserModel() {
    }

    //as anotacoes @ so servem para o atributo abaixo da linha    
    @Id
    @GeneratedValue(generator = "UUID") //igual ao auto_increment
    private UUID id;

    @Nonnull //nao pode ser nulo
    private String nome;

    @Column(unique = true) //deixa a coluna username unica
    private String username;
    private String telefone;
    private String senha;

}
