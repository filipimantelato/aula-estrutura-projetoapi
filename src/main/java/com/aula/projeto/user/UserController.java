package com.aula.projeto.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController //vai ser chamado pelo controller
@RequestMapping("/user") //requisicao http
public class UserController {

    @Autowired //vai chamar nossa classe UserController
    private IUserRepository userRepository;

    @GetMapping("/retorno") // localhost:8080/user/retorno
    public String retorno(){
        return "Hello World";
    }

    @PostMapping("/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel criar(@RequestBody UserModel userModel){
        return this.userRepository.save(userModel);
    }

    //@PostMapping
    //@PutMapping
    //@DeleteMapping
    //@PatchMapping
}
