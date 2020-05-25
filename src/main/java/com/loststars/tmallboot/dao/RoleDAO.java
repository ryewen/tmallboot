package com.loststars.tmallboot.dao;

import org.apache.ibatis.annotations.Param;

public interface RoleDAO {

    public void addUserRole(@Param(value = "userId") int userId, @Param(value = "role") String role);
}
