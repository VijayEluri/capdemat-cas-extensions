package fr.cg95.sso.cas.interceptor;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import fr.cg95.sso.cas.handler.AulWithAuthoritiesHandler;

public class LocalAuthorityFeederInterceptor extends HandlerInterceptorAdapter {

    private AulWithAuthoritiesHandler ldapService;

    public void setLdapService(AulWithAuthoritiesHandler ldapService) {
        this.ldapService = ldapService;
    }
    
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws ServletException {

        try {
            Map allLocalAuthoritiesMap = ldapService.getAllLocalAuthorities();
            request.setAttribute("localAuthorities", allLocalAuthoritiesMap);
        } catch (Exception e) {
            throw new ServletException();
        }
        return true;
    }    
}
