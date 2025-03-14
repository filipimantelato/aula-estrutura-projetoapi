package com.aula.projeto.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.aula.projeto.info.IInfoRepository;
import com.aula.projeto.info.InfoModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController //vai ser chamado pelo controller
@RequestMapping("/user") //requisicao http
public class UserController {

    @Autowired //vai chamar nossa classe UserController
    private IUserRepository userRepository;

    public UserController(IInfoRepository infoRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/listar")
    public List<UserModel> listarUser() {
        List<UserModel> usercadastrados = userRepository.findAll();
        return usercadastrados;
    }

    @PostMapping("/criar")
    private ResponseEntity criar(@RequestBody UserModel userModel, HttpServletRequest request) {
        var usernameExistente = this.userRepository.findByUsername(userModel.getUsername());
        if(usernameExistente != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já cadastrado");
        }
        else{
            var criptSenha = BCrypt.withDefaults().hashToString(12, userModel.getSenha().toCharArray());
            userModel.setSenha(criptSenha);
            var criado = this.userRepository.save(userModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity atualizarUser(@RequestBody UserModel userModel){
        var criado = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }


    @DeleteMapping("/deletar/{id}")
    public void deletar(@PathVariable UUID id){
        userRepository.deleteById(id);
    }
}
