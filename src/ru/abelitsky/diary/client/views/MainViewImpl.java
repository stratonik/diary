package ru.abelitsky.diary.client.views;

import java.util.Date;

import ru.abelitsky.diary.shared.model.DiaryRecordDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class MainViewImpl extends Composite implements MainView {

	private static MainViewImplUiBinder uiBinder = GWT.create(MainViewImplUiBinder.class);

	interface MainViewImplUiBinder extends UiBinder<Widget, MainViewImpl> {
	}

	private Presenter presenter;

	@UiField
	DatePicker calendar;
	@UiField
	Label currentDate;
	@UiField
	ToggleButton editButton;
	@UiField
	DeckLayoutPanel recordPanel;
	@UiField
	HTML recordHtml;
	@UiField
	TextArea recordSource;

	public MainViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		recordPanel.showWidget(0);
	}

	@UiHandler("calendar")
	void onCalendarValueChange(ValueChangeEvent<Date> event) {
		presenter.loadRecord(event.getValue());
	}

	@UiHandler("editButton")
	void onEditButtonValueChange(ValueChangeEvent<Boolean> event) {
		recordPanel.showWidget((event.getValue()) ? 1 : 0);
	}

	@Override
	public void setData(DiaryRecordDTO record) {
		calendar.setValue(record.getDate(), false);
		currentDate.setText(record.getDateString());
		recordHtml.setHTML(record.getHtml());
		recordSource.setText(record.getSource());

		editButton.setValue(isToday(record.getDate()), true);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@SuppressWarnings("deprecation")
	private boolean isToday(Date date) {
		Date now = new Date();
		return (date.getYear() == now.getYear()) && (date.getMonth() == now.getMonth())
				&& (date.getDate() == now.getDate());
	}
}
