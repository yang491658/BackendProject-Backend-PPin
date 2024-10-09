package projectppin.ppin.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class CustomUserDetails implements UserDetails {

    private String empID;
    private String empPw;
    private Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(String empID, String empPw, Collection<? extends GrantedAuthority> authorities) {
        this.empID = empID;
        this.empPw = empPw;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities; // 권한 리스트 반환
    }

    @Override
    public String getPassword() {
        return empPw; // 비밀번호 반환
    }

    @Override
    public String getUsername() {
        return empID; // 사용자 ID 반환
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // 계정이 만료되지 않았음을 의미
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // 계정이 잠겨 있지 않음을 의미
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // 인증 정보가 만료되지 않았음을 의미
    }

    @Override
    public boolean isEnabled() {
        return true; // 계정이 활성화되어 있음을 의미
    }
}