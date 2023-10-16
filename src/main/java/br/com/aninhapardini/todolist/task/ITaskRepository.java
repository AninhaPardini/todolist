package br.com.aninhapardini.todolist.task;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ITaskRepository
    extends JpaRepository<TaskModel, UUID> /* JpaRepository<Modelo, e formato do ID> */ {

}
