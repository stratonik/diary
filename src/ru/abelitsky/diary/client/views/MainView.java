package ru.abelitsky.diary.client.views;

import java.util.Date;

import ru.abelitsky.diary.shared.model.DiaryRecordDTO;

import com.google.gwt.user.client.ui.IsWidget;

public interface MainView extends IsWidget {

	public void onError(Throwable caught);
	
	public void setData(DiaryRecordDTO record);

	public void setPresenter(Presenter presenter);

	public interface Presenter {

		public void loadRecord(Date date);

		public void save(Date date, String recordSource);
		
		public void joinDays(Date beginDate, Date endDate, String recordSource);

	}
}
