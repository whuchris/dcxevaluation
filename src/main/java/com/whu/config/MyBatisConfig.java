package com.whu.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig
{
    @Bean
    public ConfigurationCustomizer configurationCustomizer()
    {
        ConfigurationCustomizer configurationCustomizer = new ConfigurationCustomizer()
        {

            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration)
            {
                // java 中驼峰命名和数据库中下划线字段相互转换
                configuration.setMapUnderscoreToCamelCase(true);
            }
        };
        return configurationCustomizer;
    }
}

/*
数据：
    某类型项目的id集合为List<Long> projectIdList，数目为pNum = list.size();
    某类型专家的id集合为List<Long> expertIdList,数目为eNum = list.size();

    Map<Long, List<Long>> assignment = new HashMap<>(); //创建集合来存放每个专家对应的项目列表
    //<key, value>: key：对应的专家id；value: 分配给该专家的项目id列表

    total = pNum * 7;  //该类型需要分配的总次数，项目数*7;
    quotient = total / eMum;  //平均每个专家需要分配的项目数目;
    remainder = total % eNum; //剩下的余数

    if (eNum < 7) //当该类型的专家数目小于7的时候，无法满足平均每个项目都分配给7个专家的条件
    {
        for(Long expertId : expertIdlist)
        {
            //无法满足一个项目分配7次，所以对于该类型，每个项目都要分配给所有的专家一次
            //目的是尽量使每个项目分配给的专家最大
            //专家数目很少，应该是说明该类型申请的项目也比较少
            assignment.put(expertId, projectIdList)
        }
    }
    else  //该类型的专家数目大于7.可以满足每个项目分配给7个专家的条件
    {
        int index = 0  //分配的项目的索引，projectIdList.get(index)就是当前项目的id
        for(Long expertId : expertIdlist)
        {
            List<Long> idList = new ArrayList<>(); //用来存放分配给当前专家的项目id集合
            for(i = 0 : quotient) //每个专家分配quotient次
            {
                idList.add(projectIdList.get(index));
                index = (index + 1) % pNum;      //索引+1
            }
            assignment.put(expertId, idList);  //分配给该专家的项目，存放在集合中
        }

        //循环quotient次之后，还有remainder次，也就是说这个时候很多项目都是只被分配给了6个专家
        //可以直接从expertIdList中选出索引0-remainder次的专家，分配
        for(i = 0 : remainder)
        {
            //....
        }
    }

用例if：
    //假设现在某类型有10个项目，但是只有4个专家，所以项目无法满足被分配给7个不同专家的条件
    //这个时候刚好4个专家每个专家都应该评审这是个项目，也就是10个项目，每个项目都分配给了4个专家；
用例else：
    假设现在有90个项目，9个专家；上述伪代码中
    pNum = 90;
    eNum = 9;
    totol = 90 * 7 = 630次;
    quotient = 70;
    remainder = 0;
    也就是说，每个专家刚好需要被分配70个项目，按照上述伪代码，对每个专家分配70次得到的结果就是：
    expert1 评审的项目是 0-69；
    expert2 评审的项目是 70-89 0-49；
    expert3 评审的项目是 50-89 0-29；
    expert4 评审的项目是 30-89 0-9；
    expert5 评审的项目是 10-89；
    expert6 评审的项目是 0-69；
    expert7 评审的项目是 70-89 0-49；
    expert8 评审的项目是 50-89 0-29；
    expert9 评审的项目是 30-89 0-9；
    随便选择一个项目54，发现他被分配给了1,3,4,5,6,8,9这7个专家，刚好满足条件，又能够平均分配

补充：
    上面的算法没有考虑每个专家评审的项目需要控制在50以内，优先级的条件是每个项目被分配给7个专家，
    或者每个项目被分配给尽可能多的专家；
    remainder不为0，也就是除不尽的情况下，需要重新选择一轮专家，这个需要讨论，因为有可能出现重复的问题；
    如果是后台类处理remainder，貌似是不需要管理员自己再次手动的，因为已经饱和了；
    如果后台不需要处理remainder，则可以让管理员再自己手动去选择那些未满7次的项目，进行分配
*/
