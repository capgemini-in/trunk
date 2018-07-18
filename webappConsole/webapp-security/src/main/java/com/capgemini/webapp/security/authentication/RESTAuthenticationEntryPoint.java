package com.capgemini.webapp.security.authentication;

import com.google.gson.JsonObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RESTAuthenticationEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    response.setContentType("application/json;charset=UTF-8");
    response.setHeader("Cache-Control", "no-cache");
    response.getWriter().write(buildJsonResponse(authException));
  }

  /**
   * return error in a JSON format for API level auth errors
   * @param authException
   * @return
   */
  private String buildJsonResponse(AuthenticationException authException) {
    JsonObject json = new JsonObject();
    json.addProperty("status", "Error");
    json.addProperty("message", authException.getMessage());
    return json.toString();
  }
}