package com.example.jpademo.repository;

import com.example.jpademo.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @ClassName: DocumentReposity
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/21 8:48
 * @Version: 1.0
 */
public interface DocumentReposity extends JpaRepository<Document, String> {

    @Override
    Optional<Document> findById(String s);
}
