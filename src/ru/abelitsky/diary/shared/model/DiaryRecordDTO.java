package ru.abelitsky.diary.shared.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DiaryRecordDTO implements IsSerializable {

	private Date date;
	private String dateString;
	private String source;
	private String html;

	public DiaryRecordDTO() {
	}

	public Date getDate() {
		return date;
	}

	public String getDateString() {
		return dateString;
	}

	public String getHtml() {
		return html;
	}

	public String getSource() {
		return source;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setDateString(String dateString) {
		this.dateString = dateString;
	}

	public void setHtml(String html) {
		this.html = html;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
