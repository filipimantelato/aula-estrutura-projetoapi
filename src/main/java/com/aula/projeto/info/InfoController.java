package com.aula.projeto.info;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/info")
public class InfoController {

    private final IInfoRepository infoRepository;

    public InfoController(IInfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    @GetMapping("/mensagem")
    public String mensagem(){
        return "Bem-vindo ao cadastro de pessoas!";
    }

    @PostMapping("/criar")
    private ResponseEntity criar(@RequestBody InfoModel infoModel, HttpServletRequest request) {
        var usernameExistente = this.infoRepository.findByUsername(infoModel.getUsername());
        if(usernameExistente != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já cadastrado");
        }
        else{
            System.out.println("sistema chegou aki");
            var criado = this.infoRepository.save(infoModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }
    }
}
