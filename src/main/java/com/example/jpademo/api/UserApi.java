package com.example.jpademo.api;

import com.example.jpademo.config.base.BaseApi;
import com.example.jpademo.config.base.Result;
import com.example.jpademo.config.datasource.TargetDataSource;
import com.example.jpademo.entity.SysUser;
import com.example.jpademo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName: UserApi
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/11 15:13
 * @Version: 1.0
 */
@RestController
public class UserApi extends BaseApi{
    @Autowired
    UserService userService;

    @RequestMapping("/findByName")
    public Result findByName(@RequestParam String name) {
        return success(userService.findByName(name));
    }

    /**
     * @Author tantao
     * @Description 这边调用两个service方法，已经调用了AOP了，但是并没有切换数据源，可能是
     * 这两次调用没有断开数据库的连接，所以选择的是第一个service数据源
     * @Date 8:54 2019/11/13
     * @param name
     * @param newName
     * @return com.example.jpademo.config.base.Result
     */
    @RequestMapping("/changeName")
    public Result findByName(@RequestParam String name, @RequestParam String newName) {
        SysUser byName = userService.findByName(name);
        return success(userService.changeName(byName, newName));
    }
}
