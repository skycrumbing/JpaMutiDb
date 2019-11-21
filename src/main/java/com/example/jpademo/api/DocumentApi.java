package com.example.jpademo.api;


import com.example.jpademo.config.base.BaseApi;
import com.example.jpademo.config.base.Result;
import com.example.jpademo.entity.Document;
import com.example.jpademo.service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @ClassName: DocumentApi
 * @Description: TODO
 * @Author: tantao
 * @CreateDate: 2019/11/21 8:51
 * @Version: 1.0
 */
@RestController
@RequestMapping("doc")
public class DocumentApi extends BaseApi {

    @Autowired
    DocumentService documentService;

    @GetMapping("getDocumentByID")
    public Result getDocumentByID(@RequestParam String documentID) {
        Document doc = documentService.findById(documentID);

        return success(doc);
    }

    @PostMapping("uploadDocument")
    public Result uploadDocument(@RequestParam(value = "file") MultipartFile file, HttpServletRequest request) throws IOException {
        String userId = request.getHeader("x-current-user-id");
        Document doc = documentService.uploadDocument(file, userId);
        return success(doc);
    }

    @GetMapping("deleteDocument")
    public Result uploadDocument(@RequestParam String documentID) {
        documentService.deleteDocument(documentID);
        return success(true);
    }

    @RequestMapping("downloadTemplate")
    public void downloadTemplate(@RequestParam String filename, HttpServletResponse response) throws Exception {
        documentService.downloadDocument(filename, response);
    }

    @RequestMapping("downloadById")
    public void downloadById(@RequestParam String documentID, HttpServletResponse response) throws Exception {
        documentService.downloadById(documentID, response);
    }

}
