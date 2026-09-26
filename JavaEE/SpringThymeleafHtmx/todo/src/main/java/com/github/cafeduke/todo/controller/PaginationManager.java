package com.github.cafeduke.todo.controller;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;

public class PaginationManager
{
  private static final int PAGINATION_DEFAULT_PAGE_INDEX = 0;

  private static final int PAGINATION_DEFAULT_PAGE_SIZE = 5;

  private static final int PAGINATION_DEFAULT_SLIDING_WINDOW_SIZE = 5;

  public static record PageInfo(int pageIndex, int pageSize)
  {
  }

  private PaginationManager()
  {
  }

  public static void resetPageIndex(HttpSession session, String keyPrefix)
  {
    // pageIndex in session is reset sending reqParamValue=0
    getSessionAttribute(session, keyPrefix + "page.index", 0, PAGINATION_DEFAULT_PAGE_INDEX);
  }

  /**
   * Set record PageInfo with correct value of pageIndex and pageSize after considering the following:
   * <ol>
   *   <li> Value provided by HTTP request parameter (function argument)
   *   <li> Value saved in HTTP session (if any)
   *   <li> The default value for pageIndex {@code PaginationManager.PAGINATION_DEFAULT_PAGE_INDEX} and pageSize {@code PaginationManager.PAGINATION_DEFAULT_PAGE_SIZE}
   * </ol>
   *
   * @param session HTTP session object
   * @param keyPrefix Prefix to HTTP session attributes name (Eg: key for pageIndex = <b>keyPrefix</b> + "page.index")
   * @param pageIndex The HTTP request parameter value for pageIndex (-1 by default)
   * @param pageSize The HTTP request parameter value for pageSize (-1 by default)
   * @param totalItemsCount Total number of items (adding up items in all pages)
   * @param gotoLastPage If true, pageIndex shall be set to last page's index
   * @return An immutable record PageInfo having the final pageIndex and pageSize
   */
  public static PageInfo getPageInfo(HttpSession session, String keyPrefix, int pageIndex, int pageSize, int totalItemsCount, boolean gotoLastPage)
  {
    pageIndex = getSessionAttribute(session, keyPrefix + "page.index", pageIndex, PAGINATION_DEFAULT_PAGE_INDEX);
    pageSize = getSessionAttribute(session, keyPrefix + "page.size", pageSize, PAGINATION_DEFAULT_PAGE_SIZE);

    // Determine the index of last page using total number of items and the size of each page
    int lastPageIndex = totalItemsCount == 0 ? 0 : ((totalItemsCount - 1) / pageSize);

    /**
     * pageIndex cannot be greater than lastPageIndex
     *   - Consider pageIndex pointing to last page (say 5) and the only element in this page is deleted
     *   - Now the lastPageIndex shall be calculated (as given above) to be 4, however pageIndex will still be 5
     *   - The value of pageIndex is incorrect as this page does not exist anymore. Hence, min (pageIndex, lastPageIndex)
     */
    pageIndex = (gotoLastPage) ? lastPageIndex : Math.min(pageIndex, lastPageIndex);
    return new PageInfo(pageIndex, pageSize);
  }

  /**
   * Invoke  addPaginationDetails(page, model, PAGINATION_DEFAULT_SLIDING_WINDOW_SIZE, new int[] {5, 10, 15, 20});
   *
   * @see {@link #addPaginationDetails(Page, Model, int, int[])}
   */
  public static void addPaginationDetails(Page<?> page, Model model)
  {
    addPaginationDetails(page, model, PAGINATION_DEFAULT_SLIDING_WINDOW_SIZE, new int[]
    {
      5, 10, 15, 20
    });
  }

  /**
   * Add pagination attributes to model
   * <ul>
   *   <li> page           : The {@code org.springframework.data.domain.Page} object
   *   <li> pageStartIndex : The index of the first page in the given sliding page window
   *   <li> pageEndIndex   : The index of the last page in the given sliding page window
   *   <li> pageLastIndex  : The index of the last page considering all pages
   *   <li> pageSizeArray  : An array of choices having the number of items to be displayed per page.
   * </ul>
   *
   * @param page The {@code org.springframework.data.domain.Page} object
   * @param model The {@code org.springframework.ui.Model} object
   * @param pageWindowSize The maximum number of pages shown in the pagination slider
   * @param pageSizeArray An array of choices having the number of items to be displayed per page.
   */
  public static void addPaginationDetails(Page<?> page, Model model, int pageWindowSize, int pageSizeArray[])
  {
    int pageCurrIndex = page.getNumber();
    int pageStartIndex = (pageCurrIndex / pageWindowSize) * pageWindowSize;
    int pageEndIndex = Math.min(page.getTotalPages() - 1, pageStartIndex + pageWindowSize - 1);
    int pageLastIndex = Math.max(0, page.getTotalPages() - 1);

    model.addAttribute("page", page);
    model.addAttribute("pageStartIndex", pageStartIndex);
    model.addAttribute("pageEndIndex", pageEndIndex);
    model.addAttribute("pageLastIndex", pageLastIndex);
    model.addAttribute("pageSizeArray", pageSizeArray);
  }

  /**
   * Get the value of session attribute by key=<b>name</b>
   * <ul>
   *  <li> The value shall be <b>reqParamValue</b> and session shall be updated with this new value, if <b>reqParamValue</b> is valid (>= 0)
   *  <li> If <b>reqParamValue</b> is invalid, a valid value is looked for in HTTP <b>session</b> using key <b>name</b>
   *  <li> The value shall resort to the defalutValue if <b>session</b> lookup fails.
   * </ul>
   *
   * @param session User HTTP session object
   * @param key The attribute name
   * @param reqParamValue The optional value, passed as request parameter, for the attribute name
   * @param attribDefaultValue The default value for the attribute
   * @return The final value determined by inspecting the request parameter, HTTP session and defaultValue
   */
  private static int getSessionAttribute(HttpSession session, String key, int reqParamValue, int attribDefaultValue)
  {
    int value = reqParamValue;
    if (value < 0)
    {
      Object obj = session.getAttribute(key);
      value = (obj == null) ? attribDefaultValue : Integer.valueOf(obj.toString());
    }
    else
      session.setAttribute(key, value);
    return value;
  }
}
