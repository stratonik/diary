package ru.abelitsky.diary.client.views.dialogs;

import java.util.Date;

public interface JoinDaysDialog  {

	public void setCurrentDate(Date date);
	
	public void setPresenter(Presenter presenter);

	public void showDialog();
	
	public interface Presenter {

		public void join(Date fromDate, Date toDate);

	}
}
