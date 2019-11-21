package com.example.jpademo.service;


import com.example.jpademo.config.exception.MessageException;
import com.example.jpademo.entity.Document;
import com.example.jpademo.repository.DocumentReposity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;

/**
 * @ClassName: DocumentService
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/21 8:50
 * @Version: 1.0
 */
@Service
public class DocumentService {

    @Autowired
    DocumentReposity documentReposity;

    @Value("${file.upload.root}")
    String uploadFilePath;

    @Value("${file.download.root}")
    String downloadFilePath;

    public Document findById(String documentID) {
        Optional<Document> byId = documentReposity.findById(documentID);
        if (!byId.isPresent()) {
            throw new MessageException("文档不存在");
        }
        return byId.get();
    }

    @Transactional
    public Document uploadDocument(MultipartFile file, String userId) {
        if (file == null) {
            throw new MessageException("上传的文件为空");
        }
        String name = file.getOriginalFilename();
        String suffixName = "txt";
        int index = name.lastIndexOf(".");
        if (index > 0) {
            suffixName = name.substring(index + 1);
        }
        String newName = UUID.randomUUID() + name;
        String filename = uploadFilePath + newName;
        File dest = new File(filename);
        // 判断路径是否存在，如果不存在则创建
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            // 保存到服务器中
            file.transferTo(dest);
            Document document = new Document();
            document.setExtension(suffixName);
            document.setFilename(newName);
            document.setPath(uploadFilePath);
            document.setUserId(userId);
            document.setUuid(UUID.randomUUID().toString());
            Document save = documentReposity.save(document);
            return save;
        } catch (Exception e) {
            throw new MessageException("上传失败");
        }
    }

    public void deleteDocument(String documentID) {
        Optional<Document> byId = documentReposity.findById(documentID);
        if (!byId.isPresent()) {
            throw new MessageException("数据库中没有相关文档信息");
        }
        Document document = byId.get();
        String path = document.getPath();
        String filename = document.getFilename();
        File file = new File(path + filename);
        if (file.exists()) {
            if (!file.delete()) {
                throw new MessageException("删除失败");
            }
        } else {
            throw new MessageException("文档不存在");
        }
        documentReposity.delete(document);
    }

    public void downloadDocument(String filename, HttpServletResponse response) throws IOException {
        download(response, filename, downloadFilePath);
    }

    public void downloadById(String documentID, HttpServletResponse response) throws IOException {
        Optional<Document> byId = documentReposity.findById(documentID);
        if (!byId.isPresent()) {
            throw new MessageException("数据库中没有相关文档信息");
        }
        Document document = byId.get();
        String path = document.getPath();
        String filename = document.getFilename();
        download(response, filename, path);
    }

    private void download(HttpServletResponse response, String filename, String filepath) throws IOException {
        File file = new File(filepath + filename);
        FileInputStream fis = new FileInputStream(file);
        response.setContentType("application/force-download");
        // 设置下载后的文件名以及header
        response.addHeader("Content-disposition", "attachment;fileName=" + filename);
        // 创建输出对象
        OutputStream os = response.getOutputStream();
        // 常规操作
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = fis.read(buf)) != -1) {
            os.write(buf, 0, len);
        }
        fis.close();
    }

}
