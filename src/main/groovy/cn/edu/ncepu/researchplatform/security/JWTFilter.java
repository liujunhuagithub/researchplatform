package cn.edu.ncepu.researchplatform.security;

import cn.edu.ncepu.researchplatform.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private ObjectMapper om;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            Claims jwt = JWTUtil.getWebJwt();
            String id = jwt.getSubject();
            List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList((String) (jwt.get("auth")));
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new PeopleDetails(), null, auth));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (ExpiredJwtException expire) {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setStatus(200);
            httpServletResponse.getWriter().write(om.writeValueAsString(""));
        } catch (Exception e) {
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
