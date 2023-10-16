package br.com.aninhapardini.todolist.task;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.aninhapardini.todolist.utils.Utils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

// TODO: Implementar o método de deletar uma tarefa
// TODO: Implementar o método de listar uma tarefa específica
// TODO: Implementar o método de listar as tarefas por status
// TODO: Implementar o método de listar as tarefas por prioridade
// TODO: Implementar a data e tempo de ultimo update (updateAt)

@RestController
@RequestMapping("/tasks")
public class TaskController {

  @Autowired
  private ITaskRepository taskRepository;

  @PostMapping("/")
  public ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    taskModel.setUserId((UUID) userId);

    var currentDate = LocalDateTime.now();
    if (currentDate.isAfter(taskModel.getStartAt()) || currentDate.isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(400)
          .body("A data de início / data de término não pode ser no passado");
    }

    if (taskModel.getStartAt().isAfter(taskModel.getEndAt())) {
      return ResponseEntity.status(400)
          .body("A data de início não pode ser maior que a data de término");
    }
    var task = this.taskRepository.save(taskModel);
    return ResponseEntity.status(201).body(task);

  }

  @GetMapping("/")
  public List<TaskModel> list(HttpServletRequest request) {
    var userId = request.getAttribute("userId");
    var tasks = this.taskRepository.findByUserId((UUID) userId);
    return tasks;
  }

  @PutMapping("/{id}")
  public ResponseEntity update(@RequestBody TaskModel taskModel, @PathVariable UUID id,
      HttpServletRequest request) {
    var task = this.taskRepository.findById(id).orElse(null);

    if (task == null) {
      return ResponseEntity.status(404).body("Tarefa não encontrada");
    }

    var userId = request.getAttribute("userId");

    if (!task.getUserId().equals(userId)) {
      return ResponseEntity.status(400).body("O usuário não tem permissão para editar essa tarefa");
    }

    Utils.copyNonNullProperties(taskModel, task);

    var taskUpdated = this.taskRepository.save(task);
    return ResponseEntity.status(201).body(taskUpdated);
  }

}
