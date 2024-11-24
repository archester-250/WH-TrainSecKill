package com.pku.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String email;
    private String mobile;
    private LocalDateTime registerDate;
    private LocalDateTime lastLoginDate;
    private Integer loginCount;
}
//User中的名字需于数据库一致，要不然会导致null返回值
