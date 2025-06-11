// package com.intelligentclin.clinic_service.security;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.security.web.access.AccessDeniedHandler;
// import org.springframework.stereotype.Component;

// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import java.io.IOException;
// import java.util.Date;
// import java.util.HashMap;
// import java.util.Map;

// @Component
// public class CustomAccessDeniedHandler implements AccessDeniedHandler {

//     @Override
//     public void handle(HttpServletRequest request, HttpServletResponse response,
//                        org.springframework.security.access.AccessDeniedException e) throws IOException, ServletException {
//         response.setHeader("error", e.getMessage());
//         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//         Map<String, Object> data = new HashMap<>();
//         data.put("exception", e.getMessage());
//         if (e.getMessage().contains("Token has expired"))
//             data.put("message", "Token has expired");
//         else
//             data.put("message", "acesso negado");
//         data.put("timestamp", new Date());
//         response.setContentType("application/json");
//         new ObjectMapper().writeValue(response.getOutputStream(), data);
//     }
// }