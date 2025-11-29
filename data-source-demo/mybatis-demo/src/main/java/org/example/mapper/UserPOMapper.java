package org.example.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.example.repository.UserPO;
import org.example.repository.UserPOExample;

public interface UserPOMapper {
    long countByExample(UserPOExample example);

    int deleteByExample(UserPOExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserPO row);

    int insertSelective(UserPO row);

    List<UserPO> selectByExample(UserPOExample example);

    UserPO selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("row") UserPO row, @Param("example") UserPOExample example);

    int updateByExample(@Param("row") UserPO row, @Param("example") UserPOExample example);

    int updateByPrimaryKeySelective(UserPO row);

    int updateByPrimaryKey(UserPO row);
}