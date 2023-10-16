package br.com.aninhapardini.todolist.filter;

import java.io.IOException;
import java.util.Base64;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.aninhapardini.todolist.user.IUserRepository;

@Component // Toda classe que eu quero que o Spring gerencie, eu preciso colocar essa anotação
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    var servletPath = request.getServletPath();
    if (servletPath.startsWith("/tasks/")) {
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

      var authString = new String(authDecode); /*
                                                * transformar o que foi decodificado em String
                                                */

      String[] credentials =
          authString.split(":"); /*
                                  * split é para separar o usuário e senha (no caso, o usuário é o
                                  * primeiro item e a senha é o segundo item)
                                  */
      String username = credentials[0];
      String password = credentials[1];

      // Validar usuário
      var user = this.userRepository.findByUsername(username);
      if (user == null) {
        response.sendError(401);

      } else {
        // Validar senha
        var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (passwordVerify.verified) {
          request.setAttribute("userId", user.getId());
          filterChain.doFilter(request, response);
        } else {
          response.sendError(401);
        }
        // Seguimos o fluxo


      }
    } else {
      filterChain.doFilter(request, response);
    }
  }
}
