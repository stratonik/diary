package ru.abelitsky.diary.shared.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SaveActionDTO implements IsSerializable {

	private Date date;
	private String data;

	public SaveActionDTO() {
	}

	public String getData() {
		return data;
	}

	public Date getDate() {
		return date;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
