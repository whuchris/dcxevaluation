package com.whu.service.impl;

import com.whu.mapper.ExpertMapper;
import com.whu.pojo.Expert;
import com.whu.service.ExpertService;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpertServiceImpl implements ExpertService
{
    @Autowired
    ExpertMapper expertMapper;

    @Override
    public Expert queryExpertById(Long id)
    {
        return expertMapper.queryExpertById(id);
    }

    @Override
    public Expert queryExpertByUsername(String username)
    {
        return expertMapper.queryExpertByUsername(username);
    }

    @Override
    public int updateExpert(Expert expert)
    {
        return expertMapper.updateExpert(expert);
    }

    @Override
    public List<Expert> queryAllExperts()
    {
        return expertMapper.queryAllExperts();
    }
}
