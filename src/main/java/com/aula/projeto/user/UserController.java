package com.aula.projeto.user;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private IUserRepository userRepository;

    @GetMapping("/") // localhost:8080/user/
    private String retorno(){
        return "Hello World";
    }

    @PostMapping("/criar")
    private UserModel criar(@RequestBody UserModel userModel){
        var criado = this.userRepository.save(userModel); 
        return criado;
    }

    //@PostMapping
    //@PutMapping
    //@DeleteMapping
    //@PatchMapping
}
