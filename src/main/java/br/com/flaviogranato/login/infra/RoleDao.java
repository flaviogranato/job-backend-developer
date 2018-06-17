package br.com.flaviogranato.login.infra;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDao extends JdbcDaoSupport {
  @Qualifier("role")
  @Autowired
  private RedisTemplate redisTemplate;


  @Autowired
  public RoleDao(DataSource dataSource) {
    this.setDataSource(dataSource);
  }


  public List<String> getRoleNames(Long userId) {
    final String REDIS_PREFIX = "role:";
    List<String> roles;
    String sql = "SELECT r.rolename\n" +
                 "FROM user_role ur,\n" +
                 "     roles r\n" +
                 "WHERE ur.id = r.id\n" +
                 "  AND ur.id = ?";

    Object[] params = new Object[]{userId};
    @SuppressWarnings("unchecked")
    List<String> rolesFromRedis = (List<String>) redisTemplate.opsForValue()
                                                              .get(REDIS_PREFIX + userId);
    if (rolesFromRedis != null) {
      roles = rolesFromRedis;
    } else {
      roles = this.getJdbcTemplate()
                  .queryForList(sql,
                                params,
                                String.class);
      redisTemplate.opsForValue()
                   .set(REDIS_PREFIX + userId,
                        roles);
    }
    return roles;
  }
}
