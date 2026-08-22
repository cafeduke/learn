package com.github.cafeduke.todo.controller;

import java.util.logging.Logger;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.cafeduke.todo.dto.TodoItemDto;
import com.github.cafeduke.todo.service.TodoItemService;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HtmxRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TodoItemController {

  private final TodoItemService service;

  @GetMapping({ "/work" })
  public String work() {
    return "work";
  }

  @GetMapping({ "/", "/home" })
  public String getTodoItems(@RequestParam(defaultValue = "0") int pageNumber,
      @RequestParam(defaultValue = "5") int pageSize,
      HtmxRequest htmxRequest, Model model) {

    log.info("Received pageNumber={} pageSize={}", pageNumber, pageSize);

    Page<TodoItemDto> page = service.getTodoItems(pageNumber, pageSize);
    model.addAttribute("page", page);

    return htmxRequest.isHtmxRequest() ? "fragments/core :: fragTodoList" : "home";
  }
}
