package ru.abelitsky.diary.server.services;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ru.abelitsky.diary.client.services.MainService;
import ru.abelitsky.diary.shared.model.DiaryRecordDTO;
import ru.abelitsky.diary.shared.model.SaveActionDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class MainServiceImpl extends RemoteServiceServlet implements MainService {

	private static final long serialVersionUID = 1L;

	private static final String[] WEEKDAY_NAMES = { "", "воскресенье", "понедельник", "вторник", "среда", "четверг",
			"пятница", "суббота" };

	private static final String[] MONTH_NAMES = { "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
			"августа", "сентября", "октября", "ноября", "декабря" };

	private static Map<String, String> diary = new HashMap<String, String>();

	@Override
	public DiaryRecordDTO getRecord(Date date) {
		DiaryRecordDTO result = new DiaryRecordDTO();
		result.setDate(date);

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(new Locale("ru"));
		symbols.setMonths(MONTH_NAMES);
		symbols.setWeekdays(WEEKDAY_NAMES);
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy, EEEE", symbols);
		result.setDateString(format.format(date));

		String key = new SimpleDateFormat("yyyy-MM-dd").format(date);
		String text = diary.get(key);
		if (text == null) {
			text = "";
		}
		result.setSource(text);
		result.setHtml("<p>" + text + "</p>");

		return result;
	}

	@Override
	public SaveActionDTO save(SaveActionDTO action) {
		System.out.println("Save in " + new Date());

		String key = new SimpleDateFormat("yyyy-MM-dd").format(action.getDate());
		diary.put(key, action.getData());
		action.setData("<p>" + action.getData() + "</p>");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return action;
	}

}
