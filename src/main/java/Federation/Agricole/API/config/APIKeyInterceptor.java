package Federation.Agricole.API.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class APIKeyInterceptor implements HandlerInterceptor {

    @Value("${app.api.key:agri-secure-key}")
    private String validApiKey;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestApiKey = request.getHeader("x-api-key");

        if (requestApiKey == null || !requestApiKey.equals(validApiKey)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401
            response.setContentType("text/plain");
            response.getWriter().write("Bad credentials");
            return false;
        }
        return true;
    }
}