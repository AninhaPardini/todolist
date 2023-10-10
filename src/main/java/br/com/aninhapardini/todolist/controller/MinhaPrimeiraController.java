package br.com.aninhapardini.todolist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @Controller // Mais voltado para páginas web (retorna HTML)
@RestController // Mais voltado para API REST (retorna JSON)
@RequestMapping("/hello") // http://localhost:8080/hello
public class MinhaPrimeiraController {

  @GetMapping("/")
  public String hello() {
    return "Hello World!";
  }
}
