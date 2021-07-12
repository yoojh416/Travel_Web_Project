package com.upload.files.repository;

import com.upload.files.entity.FilePath;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface FilePathRepository extends CrudRepository<FilePath, Integer>{ //제품 이미지용 repository

    FilePath findByFno(int fno);

    /*@Modifying
    @Query(value = "update FilePath f set f.fileName = :#{#files.fileName}" +
            ", f.fileOriName = :#{#files.fileOriName}" +
            ", f.fileUrl = :#{#files.fileUrl} where f.fno =:#{#files.fno}", nativeQuery = false)
    Integer update(@Param("files") FilePath files);*/

}


