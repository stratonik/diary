package ru.abelitsky.diary.server.services;

import java.io.StringWriter;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.mylyn.wikitext.core.parser.MarkupParser;
import org.eclipse.mylyn.wikitext.core.parser.builder.HtmlDocumentBuilder;
import org.eclipse.mylyn.wikitext.textile.core.TextileLanguage;

import ru.abelitsky.diary.client.services.MainService;
import ru.abelitsky.diary.server.OfyService;
import ru.abelitsky.diary.server.model.Record;
import ru.abelitsky.diary.shared.model.DiaryRecordDTO;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.googlecode.objectify.Key;
import com.googlecode.objectify.Work;
import com.googlecode.objectify.cmd.Query;

public class MainServiceImpl extends RemoteServiceServlet implements MainService {

	private static final long serialVersionUID = 2L;

	private static final String[] WEEKDAY_NAMES = { "", "воскресенье", "понедельник", "вторник", "среда", "четверг",
			"пятница", "суббота" };

	private static final String[] MONTH_NAMES = { "января", "февраля", "марта", "апреля", "мая", "июня", "июля",
			"августа", "сентября", "октября", "ноября", "декабря" };

	private static final String TAG_PATTERN = "^@tag\\s(.*?)$";

	private DiaryRecordDTO convertToDTO(Date date, Record record) {
		DiaryRecordDTO result = new DiaryRecordDTO();
		result.setDate(date);

		DateFormatSymbols symbols = DateFormatSymbols.getInstance(new Locale("ru"));
		symbols.setMonths(MONTH_NAMES);
		symbols.setWeekdays(WEEKDAY_NAMES);
		SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy, EEEE", symbols);

		String text = "";
		if (record != null) {
			if (record.getStartDate().equals(record.getEndDate())) {
				result.setDateString(format.format(record.getStartDate()));
			} else {
				result.setDateString(format.format(record.getStartDate()) + " - " + format.format(record.getEndDate()));
			}
			text = record.getText();
		} else {
			result.setDateString(format.format(date));
		}

		result.setSource(text);
		result.setHtml(convertToHtml(text));

		return result;
	}

	private String convertToHtml(String source) {
		Pattern tagPattern = Pattern.compile(TAG_PATTERN, Pattern.MULTILINE);
		Matcher tagMatcher = tagPattern.matcher(source);
		StringBuffer sb = new StringBuffer();
		while (tagMatcher.find()) {
			tagMatcher.appendReplacement(sb, "\np{font-size:smaller}. _" + formatTags(tagMatcher.group(1)) + "_");
		}
		tagMatcher.appendTail(sb);
		source = sb.toString();

		StringWriter writer = new StringWriter();
		HtmlDocumentBuilder builder = new HtmlDocumentBuilder(writer);
		builder.setEmitAsDocument(false);

		MarkupParser markupParser = new MarkupParser();
		markupParser.setMarkupLanguage(new TextileLanguage());
		markupParser.setBuilder(builder);
		markupParser.parse(source);

		return writer.toString();
	}

	private String formatTags(String tagGroup) {
		if (tagGroup != null) {
			StringBuilder sb = new StringBuilder();
			for (String tag : tagGroup.split(",")) {
				tag = tag.trim();
				if ((tag != null) && !tag.isEmpty()) {
					if (sb.length() > 0) {
						sb.append(", ");
					}
					sb.append("#").append(tag);
				}
			}
			return sb.toString();
		} else {
			return "";
		}
	}

	@Override
	public DiaryRecordDTO get(Date date) {
		date = removeTimePart(date);
		Record record = load(date);
		return convertToDTO(date, record);
	}

	private Record load(Date date) {
		Query<Record> query = OfyService.ofy().load().type(Record.class);
		Record record = query.filter("startDate <= ", date).order("-startDate").first().now();
		if ((record != null) && !record.getEndDate().before(date)) {
			return record;
		} else {
			return null;
		}
	}

	@Override
	public DiaryRecordDTO join(Date fromDate, Date toDate, Date currentDate) {
		return get(currentDate);
	}

	private Date removeTimePart(Date date) {
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return calendar.getTime();
		} else {
			return null;
		}
	}

	@Override
	public DiaryRecordDTO save(Date date, String recordText) {
		date = removeTimePart(date);

		Record record = load(date);
		if (record == null) {
			record = new Record();
			record.setStartDate(date);
			record.setEndDate(date);
		}
		record.setText(recordText);

		record = OfyService.ofy().transact(new SaveWork(record));

		return convertToDTO(date, record);
	}

	private class SaveWork implements Work<Record> {

		private Record record;

		public SaveWork(Record record) {
			this.record = record;
		}

		@Override
		public Record run() {
			Key<Record> key = OfyService.ofy().save().entity(record).now();
			return OfyService.ofy().load().key(key).now();
		}

	}

}
