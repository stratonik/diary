package ru.abelitsky.diary.server.model;

import java.util.Date;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Parent;

@Entity
public class RecordTag {

	@Id
	private Long id;
	@Parent
	private Key<Record> record;
	@Index
	private Key<Tag> tag;

	private Date startDate;
	private Date endDate;
	private String optionalValue;

	@SuppressWarnings("unused")
	private RecordTag() {
	}

	public RecordTag(Key<Record> record, Key<Tag> tag) {
		this.record = record;
		this.tag = tag;
	}

	public Long getId() {
		return id;
	}

	public Key<Record> getRecord() {
		return record;
	}

	public Key<Tag> getTag() {
		return tag;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public String getOptionalValue() {
		return optionalValue;
	}

	public void setTag(Key<Tag> tag) {
		this.tag = tag;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setOptionalValue(String optionalValue) {
		this.optionalValue = optionalValue;
	}

}
