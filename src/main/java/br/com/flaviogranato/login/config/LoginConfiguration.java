package br.com.flaviogranato.login.config;

import br.com.flaviogranato.login.domain.UserDetailsServiceImpl;
import br.com.flaviogranato.login.entity.User;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@org.springframework.context.annotation.Configuration
@EnableWebSecurity
public class LoginConfiguration extends WebSecurityConfigurerAdapter {
  @Value("${spring.redis.host}")
  private String redisHost;
  @Value("${spring.redis.port}")
  private Integer redisPort;
  @Autowired
  UserDetailsServiceImpl userDetailsService;


  @Bean
  public LettuceConnectionFactory redisConnectionFactory() {
    return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost,
                                                                         redisPort));
  }


  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }


  @Bean
  public StringRedisSerializer stringRedisSerializer() {
    return new StringRedisSerializer();
  }


  @Bean
  public Jackson2JsonRedisSerializer<User> jacksonJsonRedisJsonSerializer() {
    return new Jackson2JsonRedisSerializer<>(User.class);
  }


  @Bean(name = "user")
  @Primary
  public RedisTemplate<String,User> redisTemplateUser() {
    RedisTemplate<String,User> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(redisConnectionFactory());
    redisTemplate.setKeySerializer(stringRedisSerializer());
    redisTemplate.setValueSerializer(jacksonJsonRedisJsonSerializer());
    return redisTemplate;
  }


  @Bean(name = "role")
  public RedisTemplate<String,List<String>> redisTemplateRole() {
    RedisTemplate template = new RedisTemplate();
    template.setConnectionFactory(redisConnectionFactory());
    return template;
  }


  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder());

  }


  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable();

    http.authorizeRequests()
        .antMatchers("/",
                     "/login",
                     "/logout")
        .permitAll();

    http.authorizeRequests()
        .antMatchers("/userInfo")
        .access("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')");

    http.authorizeRequests()
        .antMatchers("/admin")
        .access("hasRole('ROLE_ADMIN')");

    http.authorizeRequests()
        .and()
        .exceptionHandling()
        .accessDeniedPage("/403");

    http.authorizeRequests()
        .and()
        .formLogin()
        .loginProcessingUrl("/j_spring_security_check")
        .loginPage("/login")
        .defaultSuccessUrl("/userInfo")
        .failureUrl("/login?error=true")
        .usernameParameter("username")
        .passwordParameter("password")
        .and()
        .logout()
        .logoutUrl("/logout")
        .logoutSuccessUrl("/logout");

  }
}

