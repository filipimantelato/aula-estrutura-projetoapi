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

/*
@RestController indica que esta classe é um controlador REST no Spring Boot. Combina @Controller e @ResponseBody, permitindo que os métodos retornem JSON diretamente.
@Controller → Para MVC tradicional (usado com @ResponseBody).

@RequestMapping("/info") define o caminho base (/info) para todas as rotas do controlador.

@GetMapping("/listar") Define que o método listarInfo() responderá a requisições HTTP GET no endpoint /info/listar. Lista as informacoes do info/listar no banco.

@PostMapping("/criar") Define que o método criar() responderá a requisições HTTP POST no endpoint /info/criar. Posta informacao na tabela info do H2

@PutMapping("/atualizar") Define que o método atualizarInfo() responderá a requisições HTTP PUT no endpoint /info/atualizar. Atualiza as informacoes na tabela info do H2

@DeleteMapping("/deletar/{id}") Define que o método deletar(UUID id) responderá a requisições HTTP DELETE no endpoint /info/deletar/{id}, onde {id} é um parâmetro dinâmico. Deleta informacoes na tabela info do h2.

ResponseEntity Representa a resposta HTTP completa, incluindo status, cabeçalhos e corpo da resposta.

UUID Identificador único universal (UUID), usado para identificar entidades de forma única.

findAll() Método do JpaRepository que retorna todos os registros do banco de dados.

findByUsername(infoModel.getUsername()) Método do InfoRepository para buscar um usuário pelo nome de usuário no banco de dados.

save(infoModel) Método do JpaRepository que salva ou atualiza um registro no banco de dados.

deleteById(id) Método do JpaRepository que deleta um registro com base no ID.

H2: Um banco de dados relacional leve, escrito em Java, que pode rodar em memória ou em modo persistente, frequentemente usado para testes e desenvolvimento em Spring Boot.

Postman: Uma ferramenta para testar APIs, permitindo enviar requisições HTTP (GET, POST, PUT, DELETE) e visualizar as respostas de forma organizada.

Spring Boot: Framework do Spring que simplifica o desenvolvimento de aplicações Java, fornecendo configuração automática e um ambiente pronto para criar APIs e microsserviços rapidamente.

API (Application Programming Interface): Conjunto de regras e definições que permite a comunicação entre sistemas, permitindo que um software utilize funcionalidades de outro através de requisições HTTP ou outros protocolos.

*/

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
        public ResponseEntity criar(@RequestBody InfoModel infoModel) {
        var existente = this.infoRepository.findByUsername(infoModel.getUsername());
        if (existente != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já possui informações cadastradas");
        }
        var criado = this.infoRepository.save(infoModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(criado);
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