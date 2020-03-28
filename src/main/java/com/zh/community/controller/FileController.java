package com.zh.community.controller;

import com.zh.community.dto.FileDTO;
import com.zh.community.exception.CustomizeErrorCode;
import com.zh.community.exception.CustomizeException;
import com.zh.community.provider.CloudFileProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

/**
 * Created by codedrinker on 2019/6/26.
 */
@Controller
@Slf4j
public class FileController {
    @Autowired
    private CloudFileProvider cloudFileProvider;

    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(@RequestParam("editormd-image-file") MultipartFile file) {
        if(file==null||file.getSize()<1){
            throw new CustomizeException(CustomizeErrorCode.UPLOAD_FILE_IS_NULL);
        }
        try {
            String fileName=file.getOriginalFilename();
            File localFile;
            String url="";
            localFile= File.createTempFile("tmp",null);
            file.transferTo(localFile);
            url=cloudFileProvider.upload(localFile,fileName);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(1);
            fileDTO.setUrl(url);
            return fileDTO;
        } catch (Exception e) {
            log.error("upload error", e);
            FileDTO fileDTO = new FileDTO();
            fileDTO.setSuccess(0);
            fileDTO.setMessage("上传失败");
            return fileDTO;
        }
    }
}
