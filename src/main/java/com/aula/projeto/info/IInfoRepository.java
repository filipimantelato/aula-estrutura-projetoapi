package com.aula.projeto.info;

import com.aula.projeto.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IInfoRepository extends JpaRepository<InfoModel, UUID> {
}
