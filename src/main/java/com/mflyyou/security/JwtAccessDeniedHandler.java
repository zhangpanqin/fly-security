package com.mflyyou.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAccessDeniedHandler implements AccessDeniedHandler {

   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
      // This is invoked when user tries to access a secured REST resource without the necessary authorization
      // We should just send a 403 Forbidden response because there is no 'error' page to redirect to
      // Here you can place any message you want
      // 这个也可以返回具体的 json 数据
      response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
   }
}