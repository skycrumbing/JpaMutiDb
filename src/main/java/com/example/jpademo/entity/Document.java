package com.example.jpademo.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName: Document
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/20 9:28
 * @Version: 1.0
 */
@Entity
@Table
@Data
@DynamicUpdate
@DynamicInsert
public class Document implements Serializable {

    @Id
    @Column(length = 36)
    private String uuid;

    /**
     * 用户id
     */
    @Column(length = 36)
    private String userId;

    /**
     * 路径
     */
    private String path;

    /**
     * 文件名
     */
    private String filename;

    /**
     * 扩展名
     */
    private String extension;

    /**
     * 文件类型
     */
    private Integer fileType;
}
