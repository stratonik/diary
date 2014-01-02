package ru.abelitsky.diary.client.services;

import java.util.Date;

import ru.abelitsky.diary.shared.model.DiaryRecordDTO;
import ru.abelitsky.diary.shared.model.SaveActionDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MainServiceAsync {

	void getRecord(Date date, AsyncCallback<DiaryRecordDTO> callback);

	void save(SaveActionDTO action, AsyncCallback<SaveActionDTO> callback);

}
