package ru.abelitsky.diary.client.activities;

import java.util.Date;

import com.google.gwt.user.client.rpc.AsyncCallback;

import ru.abelitsky.diary.client.ClientFactory;
import ru.abelitsky.diary.client.views.MainView;
import ru.abelitsky.diary.shared.model.DiaryRecordDTO;
import ru.abelitsky.diary.shared.model.SaveActionDTO;
import ru.abelitsky.diary.shared.model.SaveActionDTO.PostSaveAction;

public class MainActivity implements MainView.Presenter {

	private ClientFactory clientFactory;

	public MainActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void loadRecord(Date date) {
		clientFactory.getMainService().getRecord(date, new AsyncCallback<DiaryRecordDTO>() {
			@Override
			public void onSuccess(DiaryRecordDTO result) {
				clientFactory.getMainView().setData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

	@Override
	public void save(Date date, String recordSource, PostSaveAction postSaveAction) {
		SaveActionDTO saveAction = new SaveActionDTO();
		saveAction.setDate(date);
		saveAction.setData(recordSource);
		saveAction.setPostSaveAction(postSaveAction);

		clientFactory.getMainService().save(saveAction, new AsyncCallback<SaveActionDTO>() {
			@Override
			public void onSuccess(SaveActionDTO result) {
				clientFactory.getMainView().updateDate(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}
		});
	}

}
