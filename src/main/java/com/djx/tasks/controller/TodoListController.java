package com.djx.tasks.controller;

import com.djx.tasks.database.Task;
import com.djx.tasks.database.TaskRepository;
import com.djx.tasks.database.TodoList;
import com.djx.tasks.database.TodoListRepository;
import com.djx.tasks.exception.TodoListNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Optional;

@Controller
public class TodoListController {

    @Autowired private TodoListRepository todoListRepository;
    @Autowired private TaskRepository taskRepository;

    @RequestMapping(method = RequestMethod.GET, path = "/lists")
    @ResponseBody
    public Iterable<TodoList> findAll(@RequestParam(required = false) String searchString) {
        if (searchString == null) {
            return todoListRepository.findAll();
        } else {
            return todoListRepository.findByNameContainingOrDescriptionContaining(searchString, searchString);
        }
    }

    @RequestMapping(method = RequestMethod.POST, path = "/lists")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String create(@RequestBody TodoList resource) {
        return todoListRepository.save(resource).getId();
    }

    @RequestMapping(method = RequestMethod.GET, path = "/list/{id}")
    @ResponseBody
    public TodoList findById(@PathVariable String id) {
        Optional<TodoList> list = todoListRepository.findById(id);
        if (!list.isPresent()) {
            throw new TodoListNotFoundException("List not found");
        }
        return list.get();
    }

    @RequestMapping(method = RequestMethod.POST, path = "/list/{id}/tasks")
    @ResponseBody
    public String addNewTask(@PathVariable String id, @RequestBody Task task) {
        Optional<TodoList> list = todoListRepository.findById(id);
        if (!list.isPresent()) {
            throw new TodoListNotFoundException("List not found");
        }

        task.setTodoList(list.get());
        taskRepository.save(task);

        return "successful operation";
    }

    @RequestMapping(method = RequestMethod.POST, path = "/list/{id}/task/{taskId}/complete")
    @ResponseBody
    public String updateTask(@PathVariable String id, @RequestBody Task task, @PathVariable String taskId) {
        Task t = taskRepository.findById(taskId).get();
        t.setCompleted(true);
        taskRepository.save(t);
        return "good";
    }

}
