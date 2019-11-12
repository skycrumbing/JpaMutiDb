package com.example.jpademo.repository;

import com.example.jpademo.config.datasource.DataSourceConfig;
import com.example.jpademo.config.datasource.TargetDataSource;
import com.example.jpademo.entity.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @ClassName: UserRepository
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/11 15:07
 * @Version: 1.0
 */
public interface UserRepository extends JpaRepository<SysUser, Integer> {
    /**
     * @param name
     * @return java.util.List
     * @Author tantao
     * @Description TODO
     * @Date 15:09 2019/11/11
     */
    List<SysUser> findByName(String name);

}
