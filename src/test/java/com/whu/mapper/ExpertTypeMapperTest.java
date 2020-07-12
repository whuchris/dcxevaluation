package com.whu.mapper;

import com.whu.FcxevaluationApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ExpertTypeMapperTest extends FcxevaluationApplicationTests
{
    @Autowired
    ExpertTypeMapper expertTypeMapper;

    @Test
    public void insertExpertType()
    {
        Long expertId = 1L;
        while(expertId <= 61L)
        {
            if(expertId <= 30L)
            {
                //居住类
                for(Long prizeId = 13L; prizeId <= 20L; prizeId++)
                    expertTypeMapper.insertExpertType(expertId,prizeId,1);

                expertId++;
                continue;
            }
            else if(expertId <= 42L)
            {
                //商办类
                for(Long prizeId = 29L; prizeId <= 34L; prizeId++)
                    expertTypeMapper.insertExpertType(expertId,prizeId,1);

                //医养类
                if(expertId <= 41 && expertId >= 38)
                    for(Long prizeId = 41L; prizeId <= 48L; prizeId++)
                        expertTypeMapper.insertExpertType(expertId,prizeId,1);

                expertId++;
                continue;
            }
            else if(expertId <= 52L)
            {
                //城更类&产城类
                for(Long prizeId = 1L; prizeId <= 12L; prizeId++)
                    expertTypeMapper.insertExpertType(expertId,prizeId,1);

                expertId++;
                continue;
            }
            else if(expertId <= 60L)
            {
                //旅游类
                for(Long prizeId = 21L; prizeId <= 28L; prizeId++)
                    expertTypeMapper.insertExpertType(expertId,prizeId,1);
                //文体类
                for(Long prizeId = 35L; prizeId <= 40L; prizeId++)
                    expertTypeMapper.insertExpertType(expertId,prizeId,1);

                expertId++;
                continue;
            }
            else if(expertId == 61L)
            {
                for(Long prizeId = 41L; prizeId <= 48L; prizeId++)
                    expertTypeMapper.insertExpertType(expertId,prizeId,1);

                expertId++;
            }
        }
    }
}
