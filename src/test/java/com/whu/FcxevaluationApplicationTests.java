package com.whu;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;

@RunWith(SpringRunner.class)
@SpringBootTest

public class FcxevaluationApplicationTests
{
	protected Logger log = Logger.getLogger(FcxevaluationApplicationTests.class);

	@Autowired
	DataSource dataSource;

    @Test
	public void contextLoads() throws Exception
	{
		System.out.println("test dataSource.class:"+dataSource.getClass());
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		connection.close();
	}
}
