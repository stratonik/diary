package ru.abelitsky.diary.client.activities;

import java.util.Date;

import ru.abelitsky.diary.client.ClientFactory;
import ru.abelitsky.diary.client.views.MainView;
import ru.abelitsky.diary.shared.model.DiaryRecordDTO;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class MainActivity implements MainView.Presenter {

	private ClientFactory clientFactory;

	public MainActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void joinRecords(Date fromDate, Date toDate, Date currentDate) {
		clientFactory.getMainService().join(fromDate, toDate, currentDate, new AsyncCallback<DiaryRecordDTO>() {
			@Override
			public void onSuccess(DiaryRecordDTO result) {
				clientFactory.getMainView().setData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				clientFactory.getMainView().onError(caught);
			}
		});
	}

	@Override
	public void loadRecord(Date date) {
		clientFactory.getMainService().get(date, new AsyncCallback<DiaryRecordDTO>() {
			@Override
			public void onSuccess(DiaryRecordDTO result) {
				clientFactory.getMainView().setData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				clientFactory.getMainView().onError(caught);
			}
		});
	}

	@Override
	public void updateRecord(Date date, String recordSource) {
		clientFactory.getMainService().save(date, recordSource, new AsyncCallback<DiaryRecordDTO>() {
			@Override
			public void onSuccess(DiaryRecordDTO result) {
				clientFactory.getMainView().setData(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				clientFactory.getMainView().onError(caught);
			}
		});
	}

}
