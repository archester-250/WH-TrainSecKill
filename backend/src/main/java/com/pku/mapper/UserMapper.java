package com.pku.mapper;

import com.pku.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    @Select("select * from users where username = #{username}")
    List<User> selectUserByUsername(String username);
}
