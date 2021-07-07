package com.upload.files.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@ToString
@Table(name = "review")
public class Article { //댓글 테이블 생성용
	@Id @GeneratedValue
	private Long id;

	private String title;

	@Column(length= 100000000)
	private String content;

	private LocalDateTime registerDate;

	@Column
	private LocalDateTime updateDate;
}
