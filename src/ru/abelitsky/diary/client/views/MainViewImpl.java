package ru.abelitsky.diary.client.views;

import java.util.Date;

import ru.abelitsky.diary.shared.model.DiaryRecordDTO;
import ru.abelitsky.diary.shared.model.SaveActionDTO;
import ru.abelitsky.diary.shared.model.SaveActionDTO.PostSaveAction;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class MainViewImpl extends Composite implements MainView {

	interface MainViewImplUiBinder extends UiBinder<Widget, MainViewImpl> {
	}

	private static MainViewImplUiBinder uiBinder = GWT.create(MainViewImplUiBinder.class);

	private Presenter presenter;
	private DiaryRecordDTO record;

	@UiField
	DatePicker calendar;
	@UiField
	Label currentDate;
	@UiField
	Label saveLabel;
	@UiField
	ToggleButton editButton;
	@UiField
	DeckLayoutPanel recordPanel;
	@UiField
	HTML recordHtml;
	@UiField
	TextArea recordSource;

	private Timer saveTimer;

	public MainViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		recordPanel.showWidget(0);
	}

	private void afterSave(PostSaveAction postSaveAction) {
		saveLabel.setVisible(false);
		if (postSaveAction == PostSaveAction.SwitchToHtml) {
			recordPanel.showWidget(0);
		} else if (postSaveAction == PostSaveAction.ChangeDate) {
			presenter.loadRecord(calendar.getValue());
		}
	}

	@SuppressWarnings("deprecation")
	private boolean isDateEquals(Date date1, Date date2) {
		return (date1.getYear() == date2.getYear()) && (date1.getMonth() == date2.getMonth())
				&& (date1.getDate() == date2.getDate());
	}

	@UiHandler("calendar")
	void onCalendarValueChange(ValueChangeEvent<Date> event) {
		onSave(PostSaveAction.ChangeDate);
	}

	@UiHandler("editButton")
	void onEditButtonValueChange(ValueChangeEvent<Boolean> event) {
		if (event.getValue()) {
			recordPanel.showWidget(1);
		} else {
			onSave(PostSaveAction.SwitchToHtml);
		}
	}

	private void onSave(PostSaveAction postSaveAction) {
		if (!recordSource.getText().equals(record.getSource())) {
			record.setSource(recordSource.getText());
			presenter.save(record.getDate(), record.getSource(), postSaveAction);
			saveLabel.setVisible(true);
		} else {
			afterSave(postSaveAction);
		}
	}

	@Override
	public void setData(DiaryRecordDTO record) {
		this.record = record;

		calendar.setValue(record.getDate(), false);
		currentDate.setText(record.getDateString());
		recordHtml.setHTML(record.getHtml());
		recordSource.setText(record.getSource());

		editButton.setValue(isDateEquals(record.getDate(), new Date()), true);
	}

	@Override
	protected void onLoad() {
		super.onLoad();
		saveTimer = new Timer() {
			@Override
			public void run() {
				onSave(PostSaveAction.Nothing);
			}
		};
		saveTimer.scheduleRepeating(60 * 1000);
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		if (saveTimer != null) {
			saveTimer.cancel();
			saveTimer = null;
		}
		onSave(PostSaveAction.Nothing);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void updateDate(SaveActionDTO result) {
		if (isDateEquals(record.getDate(), result.getDate())) {
			record.setHtml(result.getData());
			recordHtml.setHTML(record.getHtml());
		}
		afterSave(result.getPostSaveAction());
	}

}
