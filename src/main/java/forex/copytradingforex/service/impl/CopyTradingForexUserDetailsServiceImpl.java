package forex.copytradingforex.service.impl;


import forex.copytradingforex.model.entity.UserEntity;
import forex.copytradingforex.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CopyTradingForexUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public CopyTradingForexUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {



        UserEntity userEntity = userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with name " + username + "not found"));
        return mapToUserDetail(userEntity);
    }




    private static UserDetails mapToUserDetail(UserEntity userEntity) {
        List<GrantedAuthority> authorities = userEntity
                .getRoles()
                .stream()
                .map(r->new SimpleGrantedAuthority("ROLE_"+ r.getRole().name()))
                 .collect(Collectors.toList());

        // if don`t use CopyTradingForexUser
       // return new User(
        return new CopyTradingForexUser(
                userEntity.getUsername(),
                userEntity.getPassword(),
                authorities
        );
    }
}
