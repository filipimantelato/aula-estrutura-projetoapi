package com.aula.projeto.user;

import java.util.*;

import com.aula.projeto.info.InfoModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public interface IUserRepository extends JpaRepository<UserModel, UUID>{
}
