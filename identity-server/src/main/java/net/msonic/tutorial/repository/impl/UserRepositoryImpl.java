package net.msonic.tutorial.repository.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import net.msonic.tutorial.common.model.User;
import net.msonic.tutorial.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public User findUserById(String name) {
		try {
			return jdbcTemplate.queryForObject("select * from users where username=?", new Object[] { name },
					new UserRowMapper());

		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}

	private class UserRowMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();

			user.setUserName(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			user.setEnabled(rs.getBoolean("enabled"));

			return user;
		}
	}

}
