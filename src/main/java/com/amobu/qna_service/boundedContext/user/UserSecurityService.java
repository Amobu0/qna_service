package com.amobu.qna_service.boundedContext.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    // 시큐리티가 특정 회원을 username 을 받았을 때
    // 그 username에 해당하는 회원정보를 얻는 수단
    // 시큐리티는 siteUser 테이블이 존재하는지 모르기 떄문에 , 요정도는 알려줘야 함
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<SiteUser> _siteUser = userRepository.findByUsername(username);

        if (_siteUser.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }

        SiteUser siteUser = _siteUser.get();

        // 권한을 담을 빈 리스트 생성
        List<GrantedAuthority> authorities = new ArrayList<>();

        if ("admin".equals(username)) {
            // UserRole.ADMIN.getValue() == "ROLE_ADMIN"

            // admin == 관리자 권한 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {

            // admin != 일반사용자 권한 부여
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }

        // Spring Security에 의한 User Class 존재
        return new User(siteUser.getUsername(), siteUser.getPassword(), authorities);
    }
}
