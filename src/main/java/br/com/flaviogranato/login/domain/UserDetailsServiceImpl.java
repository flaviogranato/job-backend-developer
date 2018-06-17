package br.com.flaviogranato.login.domain;

import br.com.flaviogranato.login.infra.RoleDao;
import br.com.flaviogranato.login.infra.UserDao;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
  @Autowired
  private UserDao userDao;
  @Autowired
  private RoleDao roleDao;


  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    br.com.flaviogranato.login.entity.User account = userDao.findUserAccount(userName);

    if (account == null) {
      throw new UsernameNotFoundException("User " + userName + " was not found in the database");
    }

    List<String> roleNames = roleDao.getRoleNames(account.getUserId());

    List<GrantedAuthority> grantList = new ArrayList<>();
    if (roleNames != null) {
      for (String role: roleNames) {
        GrantedAuthority authority = new SimpleGrantedAuthority(role);
        grantList.add(authority);
      }
    }
    return new User(account.getUserName(),
                    account.getEncrytedPassword(),
                    grantList);
  }
}
