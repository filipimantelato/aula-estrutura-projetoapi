package com.aula.projeto.info;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.aula.projeto.user.UserModel;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/info")
public class InfoController {

    private final IInfoRepository infoRepository;

    public InfoController(IInfoRepository infoRepository) {
        this.infoRepository = infoRepository;
    }

    @GetMapping("/listar")
    public List<InfoModel> listarInfo() {
        List<InfoModel> infocadastradas = infoRepository.findAll();
        return infocadastradas;
    }

    @PostMapping("/criar")
    private ResponseEntity criar(@RequestBody InfoModel infoModel, HttpServletRequest request) {
        var usernameExistente = this.infoRepository.findByUsername(infoModel.getUsername());
        if(usernameExistente != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já cadastrado");
        }
        else{
            var idUser = request.getAttribute("idUser");
            infoModel.setIdUser((UUID) idUser);
            var criado = this.infoRepository.save(infoModel);
            return ResponseEntity.status(HttpStatus.CREATED).body(criado);
        }
    }

    @PutMapping("/atualizar")
    public ResponseEntity atualizarInfo(@RequestBody InfoModel infoModel){
        var criado = this.infoRepository.save(infoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
    }


    @DeleteMapping("/deletar/{id}")
    public void deletar(@PathVariable UUID id){
        infoRepository.deleteById(id);
    }
}
