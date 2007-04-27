package fr.cg95.sso.cas.interceptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LocalAuthorityFeederInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws ServletException {

        try {
//            Map allLocalAuthoritiesMap = ldapService.getAllLocalAuthorities();
//            request.setAttribute("localAuthorities", allLocalAuthoritiesMap);
            
            request.setAttribute("localAuthority", request.getParameter("localAuthority"));
        } catch (Exception e) {
            throw new ServletException();
        }
        return true;
    }    
}
