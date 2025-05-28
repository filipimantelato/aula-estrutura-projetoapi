package com.aula.projeto.user;

import java.util.UUID;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

//@Data //gera getters e setters
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Nonnull
    public String getNome() {
        return nome;
    }

    public void setNome(@Nonnull String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

}