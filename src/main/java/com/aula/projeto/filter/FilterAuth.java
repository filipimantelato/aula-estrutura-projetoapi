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
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        var authorization = request.getHeader("Authorization");
        System.out.println("authorization");
        System.out.println(authorization);


        var authcode = authorization.substring("Basic ".length()).trim();
        System.out.println("Authorization");
        System.out.println(authcode);

        byte[] authDecoded = Base64.getDecoder().decode(authcode);
        System.out.println("Authorization");
        System.out.println(authDecoded);

        var AuthString= new String(authDecoded);
        System.out.println(AuthString);
        String[] credentials = AuthString.split(":");
        String username = credentials[0];
        String password = credentials[1];

        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        var user = userRepository.findByUsername(username);

        if (user == null) {
            response.sendError(401, "Ta errado");
        } else {
            var verificasenha= BCrypt.verifyer().verify(password.toCharArray(), user.getSenha());
            if (verificasenha.verified){
                filterChain.doFilter(request, response);
            }else {
                response.sendError(401, "TA errado");
            }
        }
    }
}
