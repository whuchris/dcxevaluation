package com.whu.service;

import com.whu.FcxevaluationApplicationTests;
import com.whu.pojo.Admin;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminServiceTest extends FcxevaluationApplicationTests
{
    @Autowired
    AdminService adminService;

    @Test
    public void queryAdminById()
    {
        Admin admin = adminService.queryAdminById(1L);
        log.info("AdminMapperTest.AdminMapperTest: " + admin.toString());
    }

    @Test
    public void queryAdminByUsername()
    {
        Admin admin = adminService.queryAdminByUsername("admin");
        log.info("AdminMapperTest.queryAdminByUsername: " + admin.toString());
    }
}
