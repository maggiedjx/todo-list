package com.djx.tasks.database;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "tasks", path = "tasks")
public interface TodoListRepository extends PagingAndSortingRepository<TodoList, String> {

    Iterable<TodoList> findByNameContainingOrDescriptionContaining(String name, String description);

}
