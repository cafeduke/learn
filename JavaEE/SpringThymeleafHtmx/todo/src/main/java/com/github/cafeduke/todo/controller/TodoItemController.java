package com.github.cafeduke.todo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.cafeduke.todo.controller.PaginationManager.PageInfo;
import com.github.cafeduke.todo.dto.TodoItemDto;
import com.github.cafeduke.todo.service.TodoItemService;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class TodoItemController
{
  private final TodoItemService service;

  private static final String FRAGMENT_UTIL_TIMESTAMP = "fragments/utils :: fragTimestamp";

  private static final String FRAGMENT_TODO_LIST = "fragments/todo-list :: fragTodoList";

  private static final String FRAGMENT_TODO_LIST_ITEM = "fragments/todo-list-item :: fragTodoListItem";

  private static final String FRAGMENT_TODO_DETAILS = "fragments/todo-details :: fragTodoDetails";

  private static final String FRAGMENT_TODO_CREATE = "fragments/todo-create :: fragTodoCreate";

  private static final String FRAGMENT_TODO_UPDATE = "fragments/todo-update :: fragTodoUpdate";

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss.SSS");

  @GetMapping(
  {
    "/", "/home"
  })
  public String index()
  {
    return "home";
  }

  @GetMapping("/work")
  public String work()
  {
    return "work";
  }

  @GetMapping("/daisy")
  public String daisy()
  {
    return "daisy";
  }

  /*
   * ----------------------------------------------------------------------------------------------------
   * List
   * ----------------------------------------------------------------------------------------------------
   */

  private String doList(Model model, HttpSession session)
  {
    return doList(false, model, session);
  }

  private String doList(boolean gotoLastPage, Model model, HttpSession session)
  {
    return doList(-1, -1, gotoLastPage, model, session);
  }

  @HxRequest
  @GetMapping("/todos")
  public String doList(@RequestParam(defaultValue = "-1") int pageIndex, @RequestParam(defaultValue = "-1") int pageSize, @RequestParam(defaultValue = "false") boolean gotoLastPage, Model model, HttpSession session)
  {
    int totalItems = (int) service.count();
    PageInfo pageInfo = PaginationManager.getPageInfo(session, "todos", pageIndex, pageSize, totalItems, gotoLastPage);
    log.info("Determined PageInfo={}", pageInfo);

    Page<TodoItemDto> page = service.findAll(pageInfo.pageIndex(), pageInfo.pageSize());
    PaginationManager.addPaginationDetails(page, model);
    return FRAGMENT_TODO_LIST;
  }

  @HxRequest
  @GetMapping("/todos/pending")
  public String doListPending(@RequestParam(defaultValue = "-1") int pageIndex, @RequestParam(defaultValue = "-1") int pageSize, Model model, HttpSession session)
  {
    int totalItems = (int) service.count();
    PageInfo pageInfo = PaginationManager.getPageInfo(session, "todos.pending", pageIndex, pageSize, totalItems, false);
    log.info("Determined PageInfo={}", pageInfo);

    return filterByCompeted(false, pageIndex, pageSize, model);
  }

  @HxRequest
  @GetMapping("/todos/completed")
  public String doListCompleted(@RequestParam(defaultValue = "-1") int pageIndex, @RequestParam(defaultValue = "-1") int pageSize, Model model, HttpSession session)
  {
    int totalItems = (int) service.count();
    PageInfo pageInfo = PaginationManager.getPageInfo(session, "todos.compeleted", pageIndex, pageSize, totalItems, false);
    log.info("Determined PageInfo={}", pageInfo);

    return filterByCompeted(true, pageIndex, pageSize, model);
  }

  /*
   * ----------------------------------------------------------------------------------------------------
   * Read
   * ----------------------------------------------------------------------------------------------------
   */

  @HxRequest
  @GetMapping("/todos/{id}")
  public String doItemDetails(@PathVariable("id") Long id, Model model)
  {
    TodoItemDto item = service.findById(id);
    model.addAttribute("item", item);
    model.addAttribute("id", id);
    return FRAGMENT_TODO_DETAILS;
  }

  /*
   * ----------------------------------------------------------------------------------------------------
   * Create
   * ----------------------------------------------------------------------------------------------------
   */

  @HxRequest
  @GetMapping("/todos/create")
  public String doCreateForm(Model model)
  {
    model.addAttribute("item", TodoItemDto.empty());
    return FRAGMENT_TODO_CREATE;
  }

  @HxRequest
  @PostMapping("/todos/create")
  public String doCreate(@Valid @ModelAttribute("item") TodoItemDto item, BindingResult result, Model model, HttpSession session)
  {
    if (result.hasErrors())
    {
      model.addAttribute("item", item);
      return FRAGMENT_TODO_CREATE;
    }
    service.save(item);
    return doList(true, model, session);
  }

  @HxRequest
  @PostMapping("/todos/{id}/toggle")
  public String doToggle(@PathVariable("id") Long id, @RequestParam("itemCount") Integer itemCount, Model model)
  {
    TodoItemDto updatedItem = service.toggleCompletedStatus(id);
    model.addAttribute("item", updatedItem);
    model.addAttribute("itemCount", itemCount);
    return FRAGMENT_TODO_LIST_ITEM;
  }

  @HxRequest
  @PostMapping("/todos/toggle-all")
  public String doToggleAll()
  {
    service.toggleCompletedStatus();
    return FRAGMENT_TODO_LIST;
  }

  /*
   * ----------------------------------------------------------------------------------------------------
   * Update
   * ----------------------------------------------------------------------------------------------------
   */

  @HxRequest
  @GetMapping("/todos/{id}/update")
  public String doUpdateForm(@PathVariable("id") Long id, Model model)
  {
    TodoItemDto item = service.findById(id);
    model.addAttribute("item", item);
    return FRAGMENT_TODO_UPDATE;
  }

  @HxRequest
  @PostMapping("/todos/update")
  public String doUpdate(@Valid @ModelAttribute("item") TodoItemDto item, BindingResult result, Model model, HttpSession session)
  {
    if (result.hasErrors())
    {
      model.addAttribute("item", item);
      return FRAGMENT_TODO_UPDATE;
    }
    service.save(item);
    return doList(model, session);
  }

  /*
   * ----------------------------------------------------------------------------------------------------
   * Delete
   * ----------------------------------------------------------------------------------------------------
   */
  @HxRequest
  @DeleteMapping("/todos/{id}/delete")
  public String doDelete(@PathVariable("id") Long id, Model model, HttpSession session)
  {
    service.deleteById(id);
    return doList(model, session);
  }

  /*
   * ----------------------------------------------------------------------------------------------------
   * Util fragments
   * ----------------------------------------------------------------------------------------------------
   */

  @GetMapping("/timestamp")
  public String timestamp(Model model)
  {
    model.addAttribute("timestamp", dateFormat.format(new Date()));
    return FRAGMENT_UTIL_TIMESTAMP;
  }

  /*
   * ----------------------------------------------------------------------------------------------------
   * Util functions
   * ----------------------------------------------------------------------------------------------------
   */

  private String filterByCompeted(boolean completed, int pageIndex, int pageSize, Model model)
  {
    Page<TodoItemDto> page = service.findByCompleted(completed, pageIndex, pageSize);
    PaginationManager.addPaginationDetails(page, model);
    return FRAGMENT_TODO_LIST;
  }

  public enum ListFilter
  {
    ALL, ACTIVE, COMPLETED
  }
}
