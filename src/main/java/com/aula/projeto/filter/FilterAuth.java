package com.aula.projeto.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.aula.projeto.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

/*
BCrypt é uma biblioteca usada para criptografar senhas de forma segura.
BCrypt.withDefaults().hashToString(12, senha.toCharArray()) → Gera um hash de senha com fator de custo 12.

HttpServletRequest representa uma requisição HTTP enviada pelo cliente para o servidor.
request.getServletPath() → Obtém o caminho da requisição.
request.getHeader("Authorization") → Obtém o cabeçalho Authorization.
request.getParameter("nome") → Obtém um parâmetro da requisição (exemplo: ?nome=João).

HttpServletResponse representa a resposta HTTP que o servidor envia ao cliente.
response.sendError(401, "Usuário sem autorização!") → Envia um erro HTTP 401 (não autorizado).
response.setStatus(200) → Define o status HTTP da resposta.
response.getWriter().write("Mensagem") → Escreve uma mensagem no corpo da resposta.

FilterChain representa a cadeia de filtros HTTP.
filterChain.doFilter(request, response) → Continua a execução do próximo filtro ou do controlador se for o último filtro.

OncePerRequestFilter classe base do Spring que garante que um filtro seja executado apenas uma vez por requisição.

Base64 classe usada para codificar e decodificar dados no formato Base64, frequentemente usado em autenticação HTTP básica.
Base64.getDecoder().decode(authEncode) → Decodifica uma string Base64.
Base64.getEncoder().encodeToString(bytes) → Codifica bytes para Base64.

@Component define um componente gerenciado pelo Spring, permitindo que seja injetado em outros lugares com @Autowired.

@Autowired injeta automaticamente um bean gerenciado pelo Spring, evitando a necessidade de instanciá-lo manualmente.

*/

@Component
public class FilterAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var serverletPath = request.getServletPath();

        if(serverletPath.equals("/info/criar")){
            var authorization = request.getHeader("Authorization");
            var authEncode = authorization.substring("Basic".length()).trim(); //remove a palavra "Basic" do cabeçalho Authorization, deixando apenas a parte codificada em Base64.
            byte[] authDecoded = Base64.getDecoder().decode(authEncode);
            System.out.println("Authorization");
            System.out.println(authDecoded);

            var authString= new String(authDecoded);
            String[] credentials = authString.split(":"); //split divide a string do cabeçalho Authorization em nome de usuário e senha usando : como separador.
            String username = credentials[0];
            String password = credentials[1];

            System.out.println("Username: " + username);
            System.out.println("Password: " + password);

            var user = this.userRepository.findByUsername(username);
            if (user == null) {
                response.sendError(401, "Usuário sem autorização!");
            } else {
                var verificaSenha= BCrypt.verifyer().verify(password.toCharArray(), user.getSenha());
                if (verificaSenha.verified){
                    request.setAttribute("idUser", user.getId()); //inclue o ID na tabela do banco para verificar a igualdade
                    filterChain.doFilter(request, response);
                }else {
                    response.sendError(401);
                }
            }

        }
        else{
            filterChain.doFilter(request, response);
        }
    }
}

