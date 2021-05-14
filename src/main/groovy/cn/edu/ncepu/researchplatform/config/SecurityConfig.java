package cn.edu.ncepu.researchplatform.config;

import cn.edu.ncepu.researchplatform.common.R;
import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.common.exception.CustomExceptionType;
import cn.edu.ncepu.researchplatform.security.CustomizerProvider;
import cn.edu.ncepu.researchplatform.security.JWTFilter;
import cn.edu.ncepu.researchplatform.security.PeopleDetails;
import cn.edu.ncepu.researchplatform.utils.JWTUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
    @Autowired
    private JWTFilter jwtFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        CustomizerProvider customizerProvider = new CustomizerProvider();
        customizerProvider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        customizerProvider.setUserDetailsService(userDetailsService);
        auth.authenticationProvider(customizerProvider);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.httpFirewall(null);
//        web.ignoring().antMatchers("/**/*.css", "/**/*.jpg", "/**/*.js", "/**/*.png");
        web.ignoring().antMatchers("/v2/api-docs",//swagger api json
                "/swagger-resources/configuration/ui",//用来获取支持的动作
                "/swagger-resources",//用来获取api-docs的URI
                "/swagger-resources/configuration/security",//安全选项
                "/swagger-ui.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//AuthenticationEntryPoint 用来解决匿名用户访问无权限资源时的异常
        //AccessDeineHandler 用来解决认证过的用户访问无权限资源时的异常
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
//          匿名访问失败处理
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(200);
            response.getWriter().write(om().writeValueAsString(R.fail(CustomException.AUTH_ERROR_Exception)));
        }).accessDeniedHandler((request, response, accessDeniedException) -> {
//            鉴权失败处理器
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(200);
            response.getWriter().write(om().writeValueAsString(R.fail(CustomException.AUTH_ERROR_Exception)));
        });


        http.formLogin().successHandler((request, response, authentication) -> {
            response.setContentType("text/plain;charset=utf-8");
            response.setStatus(200);
            response.getWriter().write(om().writeValueAsString(R.success(JWTUtil.createjwt(authentication))));
        }).failureHandler((request, response, exception) -> {
            response.setContentType("application/json; charset=utf-8");
            response.setStatus(200);
            response.getWriter().write(om().writeValueAsString(R.fail(401, "登陆失败!")));
        }).permitAll();


        http.addFilterAfter(jwtFilter, LogoutFilter.class);


        http.authorizeRequests()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/admin/**").hasAuthority("admin")
                .antMatchers(HttpMethod.DELETE).authenticated()
                .antMatchers(HttpMethod.PUT).authenticated()
                .antMatchers(HttpMethod.POST).authenticated()

                .anyRequest().permitAll();

        http.cors();
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public ObjectMapper om() {
        return new ObjectMapper();
    }
}
