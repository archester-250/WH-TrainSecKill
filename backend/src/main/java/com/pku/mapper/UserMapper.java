<<<<<<< HEAD
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
=======
package com.pku.mapper;

import com.pku.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface UserMapper {
    void insertUser(User user);
    void updateUser(User user);
    @Select("select * from t_user where id = #{userid}")
    List<User> selectUserById(Long id);
    @Select("select * from t_user where nickname = #{username}")
    List<User> selectUserByUsername(String username);
    @Select("select * from t_user where email = #{email}")
    List<User> selectUserByEmail(String email);
    @Select("select * from t_user where mobile = #{mobile}")
    List<User> selectUserByMobile(String mobile);
}
>>>>>>> seckill
