package com.github.cafeduke.todo.controller;

import jakarta.servlet.http.HttpSession;

public class SessionManager
{
  private static Object getSessionAttribute(HttpSession session, String key, Object reqParamValue)
  {
    Object obj = reqParamValue;
    if (obj == null)
      obj = session.getAttribute(key);
    else
      session.setAttribute(key, obj);
    return obj;
  }
}
