package com.example.jpademo.api;

import com.example.jpademo.config.base.BaseApi;
import com.example.jpademo.config.base.Result;
import com.example.jpademo.config.datasource.TargetDataSource;
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

    @RequestMapping("/changeName")
    public Result findByName(@RequestParam String name, @RequestParam String newName) {
        return success(userService.changeName(name,newName));
    }
}
