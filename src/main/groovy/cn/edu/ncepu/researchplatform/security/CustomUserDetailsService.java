package cn.edu.ncepu.researchplatform.security;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import cn.edu.ncepu.researchplatform.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PeopleService peopleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PeopleDetails details = peopleService.findByUsername(username);
        String referer = Utils.getRequest().getHeader(HttpHeaders.REFERER);
        if (StringUtils.hasText(referer) && referer.contains("backindex")) {
            if (!details.getAuthorities().contains(new SimpleGrantedAuthority("admin"))) {
                throw CustomException.AUTH_ERROR_Exception;
            }
        }
        return details;
    }
}
