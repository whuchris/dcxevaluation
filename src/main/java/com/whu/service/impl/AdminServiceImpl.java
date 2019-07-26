package com.whu.service.impl;

import com.whu.mapper.AdminMapper;
import com.whu.pojo.Admin;
import com.whu.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService
{
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin queryAdminById(Long id)
    {
        return adminMapper.queryAdminById(id);
    }

    @Override
    public Admin queryAdminByUsername(String username)
    {
        return adminMapper.queryAdminByUsername(username);
    }

    @Override
    public int queryStateById(Long id)
    {
        return adminMapper.queryFirstStateById(id);
    }

    @Override
    public int getFirstState(){return adminMapper.getFirstState();}

    @Override
    public int getLastState(){return adminMapper.getLastState();}
}
