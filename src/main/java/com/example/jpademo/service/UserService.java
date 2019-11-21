package com.example.jpademo.service;

import com.example.jpademo.config.datasource.DataSourceConfig;
import com.example.jpademo.config.datasource.TargetDataSource;
import com.example.jpademo.config.exception.MessageException;
import com.example.jpademo.entity.SysUser;
import com.example.jpademo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static sun.audio.AudioDevice.device;

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

    public Page<SysUser> findByPage(SysUser user, Pageable pageable) {
        if (user == null) {
            return userRepository.findAll(pageable);
        }
        //动态查询
        Specification<SysUser> specification = (Specification<SysUser>) (root, criteriaQuery, cb) -> {
            //存放查询条件
            List<Predicate> predicatesList = new ArrayList<>();
            //like模糊查询
            String name = user.getName();
            if (!StringUtils.isEmpty(name)) {
                Predicate namePredicate = cb.like(root.get("name"), '%' + name + '%');
                predicatesList.add(namePredicate);
            }
            Integer id = user.getId();
            if (id != null) {
                Predicate idPredicate = cb.equal(root.get("id"), id);
                predicatesList.add(idPredicate);
            }
            //最终将查询条件拼好然后return
            return cb.and(predicatesList.toArray(new Predicate[predicatesList.size()]));
        };
        Page<SysUser> all = userRepository.findAll(specification, pageable);
        return all;

    }
}
