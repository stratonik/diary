package ru.abelitsky.diary.client.services;

import java.util.Date;

import ru.abelitsky.diary.shared.model.DiaryRecordDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MainServiceAsync {

	void getRecord(Date date, AsyncCallback<DiaryRecordDTO> callback);

}
