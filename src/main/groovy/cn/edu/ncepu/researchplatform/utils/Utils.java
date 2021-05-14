package cn.edu.ncepu.researchplatform.utils;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

public class Utils {
    public static String verfyCode="verfyCode";
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) (RequestContextHolder.getRequestAttributes())).getRequest();
    }

    public static boolean isAdmin(){
         return SecurityContextHolder.getContext().getAuthentication().getAuthorities().contains(new SimpleGrantedAuthority("admin"));
    }
}
