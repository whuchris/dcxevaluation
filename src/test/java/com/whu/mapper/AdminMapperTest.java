package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Admin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminMapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    AdminMapper adminMapper;

    @Test
    public void queryAdminById()
    {
        Admin admin = adminMapper.queryAdminById(1L);
        log.info("AdminMapperTest.AdminMapperTest: " + admin.toString());
    }

    @Test
    public void queryAdminByUsername()
    {
        Admin admin = adminMapper.queryAdminByUsername("admin");
        log.info("AdminMapperTest.queryAdminByUsername: " + admin.toString());
    }
}
