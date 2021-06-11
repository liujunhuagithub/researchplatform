package cn.edu.ncepu.researchplatform.security;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.service.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PeopleService peopleService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PeopleDetails details = peopleService.findByUsername(username);
        return details;
    }
}
