package com.upload.files.repository;

import com.upload.files.entity.FilePath;
import org.springframework.data.repository.CrudRepository;

public interface FilePathRepository extends CrudRepository<FilePath, Integer> {

    FilePath findByFno(int fno);

}


