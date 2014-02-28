package ru.abelitsky.diary.client.services;

import java.util.Date;

import ru.abelitsky.diary.shared.model.DiaryRecordDTO;
import ru.abelitsky.diary.shared.model.SaveActionDTO;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("mainService")
public interface MainService extends RemoteService {

	public DiaryRecordDTO getRecord(Date date);

	public DiaryRecordDTO save(SaveActionDTO action);

}
