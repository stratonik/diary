package ru.abelitsky.diary.server;

import ru.abelitsky.diary.server.model.Record;
import ru.abelitsky.diary.server.model.RecordTag;
import ru.abelitsky.diary.server.model.Tag;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public final class OfyService {

	static {
		factory().register(Record.class);
		factory().register(Tag.class);
		factory().register(RecordTag.class);
	}
	
	public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }

}
