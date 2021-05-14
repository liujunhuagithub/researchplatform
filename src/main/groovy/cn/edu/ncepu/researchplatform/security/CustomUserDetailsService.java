package cn.edu.ncepu.researchplatform.security;

import cn.edu.ncepu.researchplatform.common.exception.CustomException;
import cn.edu.ncepu.researchplatform.common.exception.CustomExceptionType;
import cn.edu.ncepu.researchplatform.mapper.PeopleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private PeopleMapper peopleMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PeopleDetails details = peopleMapper.findByUsername(username);
        if ("black".equals(details.getAuth())) throw new CustomException(CustomExceptionType.AUTH_ERROR);
        return details;
    }
}
