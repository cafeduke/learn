package com.github.cafeduke.todo.controller;

import jakarta.servlet.http.HttpSession;

public class SessionManager
{
  public static <T> T getSessionAttribute(HttpSession session, String key, T defaultValue)
  {
    return getSessionAttribute(session, key, defaultValue, null);
  }

  @SuppressWarnings("unchecked")
  public static <T> T getSessionAttribute(HttpSession session, String key, T defaultValue, T reqParamValue)
  {
    T obj = reqParamValue;
    if (obj == null)
    {
      // reqParamValue is null, check session, if that's null too then use the default value
      obj = (T) session.getAttribute(key);
      obj = obj == null ? defaultValue : obj;
    }
    else
      // reqParmValue exists, so update session and return the obj
      session.setAttribute(key, obj);
    return obj;
  }
}
