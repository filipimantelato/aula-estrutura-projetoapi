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

@Component
public class FilterAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        var serverletPath = request.getServletPath();

        if(serverletPath.equals("/info/criar")){
            var authorization = request.getHeader("Authorization");
            var authEncode = authorization.substring("Basic".length()).trim();
            byte[] authDecoded = Base64.getDecoder().decode(authEncode);
            System.out.println("Authorization");
            System.out.println(authDecoded);

            var authString= new String(authDecoded);
            String[] credentials = authString.split(":");
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

