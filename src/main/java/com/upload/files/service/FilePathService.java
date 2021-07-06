package com.upload.files.service;

import com.upload.files.entity.FilePath;
import com.upload.files.repository.FilePathRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FilePathService {


    @Autowired
    private FilePathRepository filePathRepository;

    public void save(FilePath files) {
        FilePath f = new FilePath();
        f.setFileName(files.getFileName());
        f.setFileOriName(files.getFileOriName());
        f.setFileUrl(files.getFileUrl());

        filePathRepository.save(f);
    }

}
