package com.upload.files.controller;

import com.upload.files.entity.FilePath;
import com.upload.files.entity.Product;
import com.upload.files.service.FilePathService;
import com.upload.files.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.apache.catalina.filters.ExpiresFilter;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.List;

@Controller
public class FilesController {
    //메인 이미지 파일 업로드 컨트롤러

    // FileController 에서 조인된 테이블을 이용 proNo를 반환 받아야 함
    // 사용 메서드 getProNo()

    @Autowired public static FilePathService filesService;
    @Autowired public static ProductService productService;

    @RequestMapping("/mainImg")
    public String insert() {
        return "admin/fileUpload";
    }

    @RequestMapping("/upload")
    public String fileInsert(@RequestPart MultipartFile files
            , @RequestParam("proNo") Long proNo, Model model) throws Exception {

        FilePath file = new FilePath();
        model.addAttribute("proNo", proNo);

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

        return "redirect:/admin/list";
    }

    @GetMapping(value = "/admin/list")
    public String list(Model model) {
        List<Product> products = productService.products();
        model.addAttribute("products", products);
        return "admin/fileUpload";
    }
}
