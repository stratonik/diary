package ru.abelitsky.diary.server.model;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Record {

	@Id
	private Long id;
	@Index
	private Date startDate;
	private Date endDate;
	private String text;

	public Date getEndDate() {
		return endDate;
	}

	public Long getId() {
		return id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public String getText() {
		return text;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setText(String text) {
		this.text = text;
	}

}
