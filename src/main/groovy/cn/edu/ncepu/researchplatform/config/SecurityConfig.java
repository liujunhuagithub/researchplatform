package cn.edu.ncepu.researchplatform.config;

import cn.edu.ncepu.researchplatform.common.R;
import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.common.exception.CustomExceptionType;
import cn.edu.ncepu.researchplatform.security.CustomizerProvider;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.edu.ncepu.researchplatform.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        自定义Provider
        CustomizerProvider customizerProvider = new CustomizerProvider();
        customizerProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        customizerProvider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(customizerProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**/*.css", "/**/*.jpg", "/**/*.js", "/**/*.png");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        ObjectMapper om = new ObjectMapper();
//AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
        //AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//          匿名访问失败处理
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(200);
            response.getWriter().write(om.writeValueAsString(R.fail(CustomExceptionType.AUTH_ERROR)));
        }).accessDeniedHandler((request, response, accessDeniedException) -> {
//            鉴权失败处理器
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(200);
            response.getWriter().write(om.writeValueAsString(R.fail(CustomExceptionType.AUTH_ERROR)));
        });
        http.csrf().disable();
        http.formLogin().successHandler((request, response, authentication) -> {
//            登陆成功处理器
            response.setContentType("application/json;charset=utf-8");
//            获取用户名username对应的人物ID
            response.getWriter().write(JWTUtil.createjwt((PeopleDetails) (authentication.getPrincipal())));
        }).failureHandler((request, response, exception) -> {
//            认证失败处理器
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(200);
            response.getWriter().write(om.writeValueAsString(R.fail(401, "登录失败")));
        }).permitAll();
        http.cors();
        http.addFilterAfter(new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
                //              JWT验证
                try {
                    Claims jwt = JWTUtil.getWebJwt();
                    String id = jwt.getSubject();
                    List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList((String) (jwt.get("auth")));
                    SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(new PeopleDetails(), null, auth));
                    filterChain.doFilter(request, response);
                } catch (ExpiredJwtException expire) {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(200);
                    response.getWriter().write(om.writeValueAsString(""));
                } catch (Exception e) {
                    filterChain.doFilter(request, response);
                }
            }
        }, LogoutFilter.class);
        http.authorizeRequests()
                .anyRequest().permitAll()
                .antMatchers("/admin/**").hasAuthority("admin")
                .anyRequest().anonymous();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }
}
