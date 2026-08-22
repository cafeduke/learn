package com.github.cafeduke.todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.github.cafeduke.todo.entity.TodoItem;

public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

}
