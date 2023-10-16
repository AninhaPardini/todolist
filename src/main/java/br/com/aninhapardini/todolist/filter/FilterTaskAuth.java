package br.com.aninhapardini.todolist.filter;

import java.io.IOException;
import java.util.Base64;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component // Toda classe que eu quero que o Spring gerencie, eu preciso colocar essa anotação
public class FilterTaskAuth extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // Pegar a autenticação (usuário e senha)
    var authorization = request.getHeader("Authorization");


    var authEncoded = authorization.substring("Basic".length())
        .trim(); /*
                  * substring é para pegar a // partir do 6º caractere // (Basic) e trim é para //
                  * tirar os espaços em // branco
                  */

    byte[] authDecode =
        Base64.getDecoder().decode(authEncoded); /*
                                                  * decode é para decodificar o que // foi
                                                  * codificado (no caso, o usuário e senha
                                                  */

    var authString = new String(authDecode);

    String[] credentials =
        authString.split(":"); /*
                                * split é para separar o usuário e senha (no caso, o usuário é o
                                * primeiro item e a senha é o segundo item)
                                */
    String username = credentials[0];
    String password = credentials[1];

    System.out.println("Authorization:");
    System.out.println(username);
    System.out.println(password);
    // Validar usuário

    // Validar senha

    // Seguimos o fluxo

    filterChain.doFilter(request, response);

  }
}
