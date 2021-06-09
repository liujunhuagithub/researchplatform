package cn.edu.ncepu.researchplatform.security;

import cn.edu.ncepu.researchplatform.common.R;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
public class JWTFilter extends OncePerRequestFilter {
    @Autowired
    private ObjectMapper om;
    @Autowired
    private PeopleService peopleService;
    private static final Logger logger = LoggerFactory.getLogger(JWTFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            PeopleDetails details = peopleService.findByUsername(JWTUtil.parseAuthorization());
            logger.warn("当前访问用户名： {} ", details.getUsername());
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(details.getUsername(), null, details.getAuthorities()));
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (JwtException expire) {
            httpServletResponse.setContentType("application/json;charset=utf-8");
            httpServletResponse.setStatus(200);
            httpServletResponse.getWriter().write(om.writeValueAsString(R.fail(401, "请重新登录！")));
        } catch (RuntimeException e) {
            logger.warn("当前无JWT令牌");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }
    }
}
