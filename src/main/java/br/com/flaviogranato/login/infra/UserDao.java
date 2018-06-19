package br.com.flaviogranato.login.infra;

import br.com.flaviogranato.login.entity.User;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao extends JdbcDaoSupport {
  @Qualifier("user")
  @Autowired
  private RedisTemplate<String,User> redisTemplate;
  @Qualifier("users")
  @Autowired
  private RedisTemplate<String,List<User>> redisTemplateUsers;


  @Autowired
  public UserDao(DataSource dataSource) {
    this.setDataSource(dataSource);
  }


  public User findUserAccount(String username) {
    final String REDIS_PREFIX = "user:";
    User userInfo;
    String sql = "SELECT u.id,\n" +
                 "       u.username,\n" +
                 "       u.password\n" +
                 "FROM users u\n" +
                 "WHERE u.username = ? ";

    Object[] params = new Object[]{username};
    UserMapper mapper = new UserMapper();
    try {
      User userFromRedis = redisTemplate.opsForValue()
                                        .get(REDIS_PREFIX + username);

      if (userFromRedis != null) {
        userInfo = userFromRedis;
      } else {
        userInfo = this.getJdbcTemplate()
                       .queryForObject(sql,
                                       params,
                                       mapper);
        redisTemplate.opsForValue()
                     .set(REDIS_PREFIX + username,
                          userInfo);
      }
      return userInfo;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }


  public List<User> listAll() {
    final String KEY = "users";
    List<User> users;
    String sql = "SELECT u.id,\n" +
                 "       u.username,\n" +
                 "       u.password\n" +
                 "FROM users u";

    UserMapper mapper = new UserMapper();
    try {
      List<User> usersFromRedis = redisTemplateUsers.opsForValue()
                                                    .get(KEY);

      if (usersFromRedis != null) {
        users = usersFromRedis;
      } else {
        users = this.getJdbcTemplate()
                    .query(sql,
                           mapper);
        redisTemplateUsers.opsForValue()
                          .set(KEY,
                               usersFromRedis);
      }
      return users;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }


  public User get(Integer id) {
    final String REDIS_PREFIX = "user:";
    User userInfo;
    String sql = "SELECT u.id,\n" +
                 "       u.username,\n" +
                 "       u.password\n" +
                 "FROM users u\n" +
                 "WHERE u.id = ? ";

    Object[] params = new Object[]{id};
    UserMapper mapper = new UserMapper();
    try {
      User userFromRedis = redisTemplate.opsForValue()
                                        .get(REDIS_PREFIX + id);

      if (userFromRedis != null) {
        userInfo = userFromRedis;
      } else {
        userInfo = this.getJdbcTemplate()
                       .queryForObject(sql,
                                       params,
                                       mapper);
        redisTemplate.opsForValue()
                     .set(REDIS_PREFIX + id,
                          userInfo);
      }
      return userInfo;
    } catch (EmptyResultDataAccessException e) {
      return null;
    }
  }
}