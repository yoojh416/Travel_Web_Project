package com.upload.files.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
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

	private String writer;

	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
	private LocalDateTime registerDate;

	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
	private LocalDateTime updateDate;
}
