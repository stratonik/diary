package ru.abelitsky.diary.client.services;

import java.util.Date;

import ru.abelitsky.diary.shared.model.DiaryRecordDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MainServiceAsync {

	void get(Date date, AsyncCallback<DiaryRecordDTO> callback);

	void join(Date fromDate, Date toDate, Date currentDate, AsyncCallback<DiaryRecordDTO> callback);

	void save(Date date, String record, AsyncCallback<DiaryRecordDTO> callback);

}
