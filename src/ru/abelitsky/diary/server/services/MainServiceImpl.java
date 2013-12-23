package ru.abelitsky.diary.server.services;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import ru.abelitsky.diary.client.services.MainService;
import ru.abelitsky.diary.shared.model.DiaryRecordDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MainServiceImpl extends RemoteServiceServlet implements MainService {

	private static final long serialVersionUID = 1L;

	private static final String[] WEEKDAY_NAMES = { "понедельник", "вторник", "среда", "четверг", "пятница", "суббота",
			"воскресенье" };

	private static final String[] MONTH_NAMES = { "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
			"августа", "сентября", "октября", "ноября", "декабря" };

	@Override
	public DiaryRecordDTO getRecord(Date date) {
		DiaryRecordDTO result = new DiaryRecordDTO();
		result.setDate(date);

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(new Locale("ru"));
		symbols.setMonths(MONTH_NAMES);
		symbols.setWeekdays(WEEKDAY_NAMES);
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy, EEEE", symbols);
		result.setDateString(format.format(date));
		
		result.setSource("Test!!!");
		result.setHtml("<p>Test!!!</p>");

		return result;
	}

}
