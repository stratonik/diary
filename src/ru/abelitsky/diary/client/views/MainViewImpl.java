package ru.abelitsky.diary.client.views;

import java.util.Date;

import ru.abelitsky.diary.client.ClientFactory;
import ru.abelitsky.diary.client.views.dialogs.JoinDaysDialog;
import ru.abelitsky.diary.client.views.utils.MainViewEventBus;
import ru.abelitsky.diary.client.views.utils.MainViewEventBusTask;
import ru.abelitsky.diary.shared.model.DiaryRecordDTO;
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

	private static MainViewImplUiBinder uiBinder = GWT.create(MainViewImplUiBinder.class);

	private ClientFactory clientFactory;

	private MainViewEventBus eventBus = GWT.create(MainViewEventBus.class);

	private DiaryRecordProcessTask currentTask;

	private Timer saveTimer;

	private Presenter presenter;

	private DiaryRecordDTO record;

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
		eventBus.add(new SaveTask());
		eventBus.add(new JoinDaysTask(fromDate, toDate, record.getDate()));
	}

	@UiHandler("calendar")
	void onCalendarValueChange(ValueChangeEvent<Date> event) {
		eventBus.add(new SaveTask());
		eventBus.add(new LoadTask(event.getValue()));
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
		if (!event.getValue()) {
			eventBus.add(new SaveTask());
		}
		eventBus.add(new ChangeEditModeTask(event.getValue()));
	}

	@Override
	protected void onLoad() {
		super.onLoad();

		saveTimer = new Timer() {
			@Override
			public void run() {
				eventBus.add(new SaveTask());
			}
		};
		saveTimer.scheduleRepeating(60 * 1000);
	}

	@Override
	public void onError(Throwable caught) {
		if (currentTask != null) {
			currentTask.processFault(caught);
		} else {
			new LoadTask(record.getDate()).processFault(caught);
		}
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

	@Override
	public void setData(DiaryRecordDTO record) {
		this.record = record;

		if (currentTask != null) {
			currentTask.process(record);
		} else {
			new LoadTask(record.getDate()).process(record);
		}
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	interface DiaryRecordProcessTask extends MainViewEventBusTask {

		void process(DiaryRecordDTO record);

		void processFault(Throwable caught);

	}

	abstract class AbstractDiaryRecordProcessTask implements DiaryRecordProcessTask {

		private MainViewEventBus eventBus;

		protected void startEventBus() {
			currentTask = null;
			if (eventBus != null) {
				eventBus.start();
			}
		}

		protected void stopEventBus(MainViewEventBus eventBus) {
			this.eventBus = eventBus;
			currentTask = this;
			eventBus.stop();
		}

		@Override
		public void processFault(Throwable caught) {
			currentTask = null;
			if (eventBus != null) {
				eventBus.reset();
			}
		}

	}

	class LoadTask extends AbstractDiaryRecordProcessTask {

		private Date date;

		public LoadTask(Date date) {
			this.date = date;
		}

		protected Date getDate() {
			return date;
		}

		@Override
		public void run(MainViewEventBus eventBus) {
			saveLabel.setText("Загрузка...");
			presenter.loadRecord(getDate());
			stopEventBus(eventBus);
		}

		@Override
		public void process(DiaryRecordDTO record) {
			calendar.setValue(record.getDate(), false);
			currentDateLabel.setText(record.getDateString());
			recordHtml.setHTML(record.getHtml());
			recordSourceTextArea.setText(record.getSource());

			saveLabel.setText("");
			editButton.setValue(DateUtils.isEquals(record.getDate(), new Date()), true);

			startEventBus();
		}

		@Override
		public void processFault(Throwable caught) {
			super.processFault(caught);
			saveLabel.setText("Ошибка при загрузке данных");
			if (record != null) {
				calendar.setValue(record.getDate());
			}
		}

	}

	class SaveTask extends AbstractDiaryRecordProcessTask {

		@Override
		public void run(MainViewEventBus eventBus) {
			if (!recordSourceTextArea.getText().equals(record.getSource())) {
				saveLabel.setText("Сохранение...");
				presenter.updateRecord(record.getDate(), recordSourceTextArea.getText());
				stopEventBus(eventBus);
			}
		}

		@Override
		public void process(DiaryRecordDTO record) {
			recordHtml.setHTML(record.getHtml());
			saveLabel.setText("Сохранено в "
					+ DateTimeFormat.getFormat(DateTimeFormat.PredefinedFormat.HOUR24_MINUTE).format(new Date()));
			startEventBus();
		}

		@Override
		public void processFault(Throwable caught) {
			saveLabel.setText("Ошибка сохранения!");
			calendar.setValue(record.getDate());
			editButton.setDown(true);
			super.processFault(caught);
		}

	}

	class ChangeEditModeTask implements MainViewEventBusTask {

		private boolean editMode;

		public ChangeEditModeTask(boolean editMode) {
			this.editMode = editMode;
		}

		@Override
		public void run(MainViewEventBus eventBus) {
			if (editMode) {
				recordPanel.showWidget(1);
				editButton.setDown(true);
				new Timer() {
					@Override
					public void run() {
						recordSourceTextArea.setFocus(true);
						recordSourceTextArea.setCursorPos(recordSourceTextArea.getText().length());
					}
				}.schedule(100);
			} else {
				recordPanel.showWidget(0);
				editButton.setDown(false);
			}
		}

	}

	class JoinDaysTask extends LoadTask {

		private Date fromDate;
		private Date toDate;

		public JoinDaysTask(Date fromDate, Date toDate, Date currentDate) {
			super(currentDate);
			this.fromDate = fromDate;
			this.toDate = toDate;
		}

		@Override
		public void run(MainViewEventBus eventBus) {
			saveLabel.setText("Объединение...");
			presenter.joinRecords(fromDate, toDate, super.getDate());
			stopEventBus(eventBus);
		}

		@Override
		public void processFault(Throwable caught) {
			super.processFault(caught);
			saveLabel.setText("Ошибка при объединение дней");
		}

	}
}
