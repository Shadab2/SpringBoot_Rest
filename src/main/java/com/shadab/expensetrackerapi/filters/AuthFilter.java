package com.shadab.expensetrackerapi.filters;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.shadab.expensetrackerapi.constants.Constants.API_SECRET_KEY;


public class AuthFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse) response;
        String authHeader = req.getHeader("Authorization");
        if(authHeader!=null){
            String[] authHeaderArr= authHeader.split("Bearer ");
            if(authHeaderArr.length > 1 && authHeaderArr[1]!=null){
                String token = authHeaderArr[1];
                try{
                Claims claims = Jwts.parser().setSigningKey(API_SECRET_KEY).parseClaimsJws(token).getBody();
                req.setAttribute("userId",Integer.parseInt(claims.get("userId").toString()));
                }catch (Exception e){
                    res.sendError(res.SC_FORBIDDEN,"invalid/expired token");
                    return;
                }
            }else {
                res.sendError(res.SC_FORBIDDEN,"Authorization token must be Bearer [token]");
                return;
            }
        }else {
            res.sendError(res.SC_FORBIDDEN,"Authorization Header is missing!");
            return;
        }
        chain.doFilter(request,response);
    }
}
