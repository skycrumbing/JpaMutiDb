package com.example.jpademo.service;

import com.example.jpademo.config.datasource.DataSourceConfig;
import com.example.jpademo.config.datasource.TargetDataSource;
import com.example.jpademo.config.exception.MessageException;
import com.example.jpademo.entity.SysUser;
import com.example.jpademo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.util.List;

/**
 * @ClassName: UserService
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/11 15:10
 * @Version: 1.0
 */
@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Transactional
    @TargetDataSource(dataSource = DataSourceConfig.WRITE_DATASOURCE_KEY)
    public int changeName(SysUser user, String newName) {
        user.setName(newName);
        userRepository.save(user);
        return 0;
    }

    @TargetDataSource(dataSource = DataSourceConfig.READ_DATASOURCE_KEY)
    public SysUser findByName(String name) {
        List<SysUser> byName = userRepository.findByName(name);
        if(byName!=null && !byName.isEmpty()) {
            SysUser sysUser = byName.get(0);
            return sysUser;
        }else {
            throw new MessageException("无此用户");
        }
    }
}
