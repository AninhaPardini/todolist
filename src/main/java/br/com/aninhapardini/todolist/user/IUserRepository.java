package br.com.aninhapardini.todolist.user;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserRepository
        extends JpaRepository<UserModel, UUID> /* JpaRepository<Modelo, e formato do ID> */ {

    UserModel findByUsername(String username);
}
