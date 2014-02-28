package ru.abelitsky.diary.client.views;

import java.util.Date;

import ru.abelitsky.diary.client.ClientFactory;
import ru.abelitsky.diary.client.views.dialogs.JoinDaysDialog;
import ru.abelitsky.diary.client.views.utils.MainViewEventBus;
import ru.abelitsky.diary.shared.model.DiaryRecordDTO;
import ru.abelitsky.diary.shared.model.SaveActionDTO;
import ru.abelitsky.diary.shared.utils.DateUtils;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DeckLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DatePicker;

public class MainViewImpl extends Composite implements MainView, JoinDaysDialog.Presenter {

	interface MainViewImplUiBinder extends UiBinder<Widget, MainViewImpl> {
	}

	private enum SaveType {
		autoSave, editingFinish, currentDateChange, daysJoin
	}

	private static MainViewImplUiBinder uiBinder = GWT.create(MainViewImplUiBinder.class);

	private ClientFactory clientFactory;
	private MainViewEventBus eventBus = GWT.create(MainViewEventBus.class);

	private Presenter presenter;
	private DiaryRecordDTO record;

	private Timer saveTimer;
	private SaveType saving;

	@UiField
	DatePicker calendar;
	@UiField
	Label currentDateLabel;
	@UiField
	Label saveLabel;
	@UiField
	ToggleButton editButton;
	@UiField
	DeckLayoutPanel recordPanel;
	@UiField
	HTML recordHtml;
	@UiField
	TextArea recordSourceTextArea;
	@UiField
	Button joinDaysButton;

	public MainViewImpl(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;

		initWidget(uiBinder.createAndBindUi(this));
		recordPanel.showWidget(0);
	}

	public void join(Date fromDate, Date toDate) {
		saving = SaveType.daysJoin;
		record.setSource(recordSourceTextArea.getText());
		presenter.joinDays(fromDate, toDate, recordSourceTextArea.getText());
		saveLabel.setText("Объединение...");
	}

	@UiHandler("calendar")
	void onCalendarValueChange(ValueChangeEvent<Date> event) {
		onSave(SaveType.currentDateChange);
	}

	@UiHandler("joinDaysButton")
	void onJoinDaysButtonClick(ClickEvent event) {
		JoinDaysDialog dialog = clientFactory.getJoinDaysDialog();
		dialog.setPresenter(this);
		dialog.setCurrentDate(record.getDate());
		dialog.showDialog();
	}

	@UiHandler("editButton")
	void onEditButtonValueChange(ValueChangeEvent<Boolean> event) {
		if (event.getValue()) {
			recordPanel.showWidget(1);
			new Timer() {
				@Override
				public void run() {
					recordSourceTextArea.setFocus(true);
					recordSourceTextArea.setCursorPos(recordSourceTextArea.getText().length());
				}
			}.schedule(100);
		} else {
			onSave(SaveType.editingFinish);
		}
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		saveTimer = new Timer() {
			@Override
			public void run() {
				onSave(SaveType.autoSave);
			}
		};
		saveTimer.scheduleRepeating(60 * 1000);
	}

	private void onSave(SaveType saveType) {
		if (saving == null) {
			saving = saveType;
			if (!recordSourceTextArea.getText().equals(record.getSource())) {
				record.setSource(recordSourceTextArea.getText());
				presenter.save(record.getDate(), record.getSource());
				saveLabel.setText("Сохранение...");
			} else {
				if (saveType != SaveType.autoSave) {
					saveSuccess();
				} else {
					saving = null;
				}
			}
		}
	}

	@Override
	public void onSaveError() {
		saveLabel.setText("Ошибка сохранения!");
		calendar.setValue(record.getDate());
		editButton.setDown(true);
		record.setSource("");	// Для того, чтобы в следующий раз обязательно
								// сработал вызов сохранения
		saving = null;
	}

	@Override
	protected void onUnload() {
		super.onUnload();

		if (saveTimer != null) {
			saveTimer.cancel();
			saveTimer.run();
			saveTimer = null;
		}
	}

	private void saveSuccess() {
		if (saving == SaveType.autoSave) {
			saveLabel.setText("Сохранено в "
					+ DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.HOUR24_MINUTE).format(new Date()));
		} else {
			saveLabel.setText("");
		}

		if (saving == SaveType.editingFinish) {
			recordPanel.showWidget(0);
			editButton.setDown(false);
			calendar.setValue(record.getDate());
		} else if (saving == SaveType.currentDateChange) {
			presenter.loadRecord(calendar.getValue());
		}
		saving = null;
	}

	@Override
	public void setData(DiaryRecordDTO record) {
		this.record = record;

		calendar.setValue(record.getDate(), false);
		currentDateLabel.setText(record.getDateString());
		recordHtml.setHTML(record.getHtml());
		recordSourceTextArea.setText(record.getSource());
		saveLabel.setText("");

		editButton.setValue(DateUtils.isEquals(record.getDate(), new Date()), true);
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void updateDate(SaveActionDTO result) {
		if (DateUtils.isEquals(record.getDate(), result.getDate())) {
			record.setHtml(result.getData());
			recordHtml.setHTML(record.getHtml());
		}
		saveSuccess();
	}

}
