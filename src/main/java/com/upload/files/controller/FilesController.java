package com.upload.files.controller;

import com.upload.files.entity.FilePath;
import com.upload.files.service.FilePathService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;

@Controller
public class FilesController { //메인 이미지 파일 업로드 컨트롤러

    @Autowired FilePathService filesService;

    @RequestMapping("/mainForm")
    public String Insert() {
        return "/admin/fileUpload";
    }

    @RequestMapping("/upload")
    public String fileinsert(HttpServletRequest request, @RequestPart MultipartFile files) throws Exception{
        FilePath file = new FilePath();

        String sourceFileName = files.getOriginalFilename();
        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        File destinationFile;
        String destinationFileName;
        String fileUrl = "D:/upload/main/";

        do {
            destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFile = new File(fileUrl + destinationFileName);
        } while (destinationFile.exists());

        destinationFile.getParentFile().mkdirs();
        files.transferTo(destinationFile);

        file.setFileName(destinationFileName);
        file.setFileOriName(sourceFileName);
        file.setFileUrl(fileUrl);
        filesService.save(file);
        return "/admin/itemList";
    }
}
