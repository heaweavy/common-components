package com.github.heaweavy.common.components.test;

import com.github.heaweavy.common.components.datasource.admin.dynamic.DynamicDataSource;
import com.github.heaweavy.common.components.datasource.admin.entity.User;
import com.github.heaweavy.common.components.datasource.admin.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import java.sql.*;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertNotNull;

/**
 * Created by caimb on 2017/2/8.
 */
public class DatabaseSourceTest {
    private SqlSessionFactory sqlSessionFactory;
    private DynamicDataSource dds;
    @Before
    public void init() throws Exception{
        dds = new DynamicDataSource();
        //设置c3p0
        dds.getDataSource().setDriverClass("com.mysql.jdbc.Driver");
        dds.getDataSource().setJdbcUrl("jdbc:mysql://localhost:3306/schoolims?serverTimezone=UTC");
        dds.getDataSource().setUser("root");
        dds.getDataSource().setPassword("11001100");
        dds.getDataSource().setInitialPoolSize(5);
        dds.getDataSource().setMinPoolSize(5);
        dds.getDataSource().setMaxPoolSize(15);
        dds.getDataSource().setIdleConnectionTestPeriod(60);
        dds.init();
        this.sqlSessionFactory = dds.getSqlSessionFactory();
    }
    @Test
    public void testConnent() throws SQLException {
        SqlSession session =  this.sqlSessionFactory.openSession();
        UserMapper userMapper = session.getMapper(UserMapper.class);
        User user = userMapper.getUserById(1);
//        List<Map<String,Object>> result = session.selectList("SELECT * FROM user_tbl");
//        Connection connection =session.getConnection();
        assertNotNull(user);
    }
}
