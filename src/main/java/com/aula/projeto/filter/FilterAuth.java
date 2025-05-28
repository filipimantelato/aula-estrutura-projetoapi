package com.aula.projeto.filter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.aula.projeto.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

//@Profile("!dev") // Só ativa o filtro em ambientes que não são 'dev'
@Component
public class FilterAuth extends OncePerRequestFilter {

    @Autowired
    IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
    String servletPath = request.getServletPath();
    String contentType = request.getContentType();

    // Aplica o filtro somente se for chamada da API (JSON)
    if (servletPath.equals("/info/criar") && "application/json".equalsIgnoreCase(contentType)) {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Basic ")) {
            response.sendError(401, "Cabeçalho Authorization ausente ou inválido");
            return;
        }

        String authEncode = authorization.substring("Basic".length()).trim();
        byte[] authDecoded = Base64.getDecoder().decode(authEncode);
        String authString = new String(authDecoded);
        String[] credentials = authString.split(":");

        if (credentials.length != 2) {
            response.sendError(401, "Formato de credenciais inválido");
            return;
        }

        String username = credentials[0];
        String password = credentials[1];

        var user = this.userRepository.findByUsername(username);
        if (user == null) {
            response.sendError(401, "Usuário não encontrado");
            return;
        }

        var verificaSenha = BCrypt.verifyer().verify(password.toCharArray(), user.getSenha());
        if (verificaSenha.verified) {
            request.setAttribute("idUser", user.getId());
            filterChain.doFilter(request, response);
        } else {
            response.sendError(401, "Senha incorreta");
        }
    } else {
        // Se não for requisição JSON ou não for para /info/criar, deixa passar direto
        filterChain.doFilter(request, response);
    }
}

}



