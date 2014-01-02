package ru.abelitsky.diary.shared.model;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SaveActionDTO implements IsSerializable {

	public enum PostSaveAction { Nothing, SwitchToHtml, ChangeDate }
	
	private Date date;
	private String data;
	private PostSaveAction postSaveAction;
	
	public SaveActionDTO() {
	}

	public String getData() {
		return data;
	}

	public Date getDate() {
		return date;
	}

	public PostSaveAction getPostSaveAction() {
		return postSaveAction;
	}

	public void setData(String data) {
		this.data = data;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setPostSaveAction(PostSaveAction postSaveAction) {
		this.postSaveAction = postSaveAction;
	}

	
}
