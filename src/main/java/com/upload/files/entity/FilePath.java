package com.upload.files.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@Table(name = "files")
public class FilePath {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int fno;
    String fileName;
    String fileOriName;
    String fileUrl;

}
