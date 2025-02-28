package com.aula.projeto.info;

import com.aula.projeto.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface IInfoRepository extends JpaRepository<InfoModel, UUID> {
    InfoModel findByUsername(String username);
}
