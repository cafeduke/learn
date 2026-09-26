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
import com.github.cafeduke.todo.util.Util;

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

  private static final String FRAGMENT_TODO_LIST_SEARCH_CLEAR = "fragments/todo-list-search :: fragTodoListSearchClear";

  private static final String FRAGMENT_TODO_DETAILS = "fragments/todo-details :: fragTodoDetails";

  private static final String FRAGMENT_TODO_CREATE = "fragments/todo-create :: fragTodoCreate";

  private static final String FRAGMENT_TODO_UPDATE = "fragments/todo-update :: fragTodoUpdate";

  private static final String SESSION_KEY_PAGINATION = "todos.pagination";

  public static final String SESSION_KEY_SEARCH = "todos.search";

  public static final String SESSION_KEY_SETTINGS = "todos.settings";

  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd-MMM-yyyy HH:mm:ss.SSS");

  public static void main(String arg[])
  {

  }

  @GetMapping(
  {
    "/", "/home"
  })
  public String index()
  {
    return "home";
  }

  @GetMapping("/work")
  public String work(Model model)
  {
    model.addAttribute("statusOptions", StatusOption.values());
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
    // Note: A filter parameter (Eg: completed or title) will be null when there is no need to filter. In essence, it does not add to the WHERE clause of the SQL Query

    // Get the DTOs
    SearchDto searchDto = SearchDto.getInstance(session);
    SettingsDto settingsDto = SettingsDto.getInstance(session);
    log.info("[doList] searchDto={} settingsDto={}", searchDto, settingsDto);

    // If filterByStatus==ALL then there is no need to filter by status. Otherwise, completed=true if status==COMPLETED and false if status==PENDING
    Boolean completed = (settingsDto.filterByStatus() == StatusOption.ALL) ? null : (settingsDto.filterByStatus() == StatusOption.COMPLETED);

    // If title==null|"" then there is no need to filter by title.
    String title = searchDto.filterByTitle();
    title = (title == null || title.isEmpty()) ? null : title;

    // Determine the total number of items after applying all filters
    int totalItems = (int) service.count(completed, title);

    // Get updated pagination information by analysing request-params, HTTP session and default values
    PageInfo pageInfo = PaginationManager.getPageInfo(session, SESSION_KEY_PAGINATION, pageIndex, pageSize, totalItems, gotoLastPage);
    log.info("[doList] Determined completed={} title={} PageInfo={}", completed, title, pageInfo);

    // Get page having items
    Page<TodoItemDto> page = service.filter(completed, title, pageInfo.pageIndex(), pageInfo.pageSize());

    // Update Model
    PaginationManager.addPaginationDetails(page, model);
    model.addAttribute("searchDto", searchDto);
    model.addAttribute("settingsDto", settingsDto);
    model.addAttribute("statusOptions", StatusOption.values());
    model.addAttribute("isSearchQueryEmpty", Util.isEmpty(searchDto.filterByTitle()));
    return FRAGMENT_TODO_LIST;
  }

  @HxRequest
  @GetMapping("/todos/search-query-update")
  public String doSearchQueryUpdate(@RequestParam String filterByTitle, Model model)
  {
    model.addAttribute("isSearchQueryEmpty", Util.isEmpty(filterByTitle));
    return FRAGMENT_TODO_LIST_SEARCH_CLEAR;
  }

  @HxRequest
  @GetMapping("/todos/filter/search")
  public String doFilterByTitleSearch(@ModelAttribute("searchDto") SearchDto searchDto, HttpSession session, Model model)
  {
    log.info("[doFilterByTitleSearch] key={} dto={}", SESSION_KEY_SEARCH, searchDto);
    session.setAttribute(SESSION_KEY_SEARCH, searchDto);
    PaginationManager.resetPageIndex(session, SESSION_KEY_PAGINATION);
    return doList(model, session);
  }

  @HxRequest
  @GetMapping("/todos/filter/search-reset")
  public String doFilterByTitleSearchReset(HttpSession session, Model model)
  {
    session.setAttribute(SESSION_KEY_SEARCH, SearchDto.getDefaultInstance());
    PaginationManager.resetPageIndex(session, SESSION_KEY_PAGINATION);
    return doList(model, session);
  }

  @HxRequest
  @GetMapping("/todos/filter/settings")
  public String doFilterBySettings(@ModelAttribute("settingsDto") SettingsDto settingsDto, HttpSession session, Model model)
  {
    log.info("[doFilterBySettings] key={} dto={}", SESSION_KEY_SETTINGS, settingsDto);
    session.setAttribute(SESSION_KEY_SETTINGS, settingsDto);
    PaginationManager.resetPageIndex(session, SESSION_KEY_PAGINATION);
    return doList(model, session);
  }

  @HxRequest
  @GetMapping("/todos/filter/settings-reset")
  public String doFilterBySettingsReset(HttpSession session, Model model)
  {
    session.setAttribute(SESSION_KEY_SETTINGS, SettingsDto.getDefaultInstance());
    PaginationManager.resetPageIndex(session, SESSION_KEY_PAGINATION);
    return doList(model, session);
  }

  /*
   * ----------------------------------------------------------------------------------------------------
   * Read
   * ----------------------------------------------------------------------------------------------------
   */

  @HxRequest
  @GetMapping("/todos/{id}")
  public String doShowDetails(@PathVariable("id") Long id, Model model)
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

  public static record SearchDto(String filterByTitle)
  {
    public static SearchDto getDefaultInstance()
    {
      return new SearchDto(null);
    }

    public static SearchDto getInstance(HttpSession session)
    {
      Object obj = session.getAttribute(SESSION_KEY_SEARCH);
      return (obj == null) ? getDefaultInstance() : (SearchDto) obj;
    }
  }

  public static record SettingsDto(StatusOption filterByStatus)
  {
    public SettingsDto(String filterByStatus)
    {
      this(StatusOption.valueOf(filterByStatus.toUpperCase()));
    }

    public static SettingsDto getDefaultInstance()
    {
      return new SettingsDto(StatusOption.ALL);
    }

    public static SettingsDto getInstance(HttpSession session)
    {
      Object obj = session.getAttribute(SESSION_KEY_SETTINGS);
      return (obj == null) ? getDefaultInstance() : (SettingsDto) obj;
    }
  }

  public enum SortOption
  {
    TITLE, STATUS, CREATION_DATE;

    @Override
    public String toString()
    {
      char ch[] = this.name().replace('_', ' ').toLowerCase().toCharArray();
      ch[0] = Character.toUpperCase(ch[0]);
      return String.valueOf(ch);
    }
  }

  public enum StatusOption
  {
    ALL, PENDING, COMPLETED;

    @Override
    public String toString()
    {
      char ch[] = this.name().replace('_', ' ').toLowerCase().toCharArray();
      ch[0] = Character.toUpperCase(ch[0]);
      return String.valueOf(ch);
    }
  }
}
