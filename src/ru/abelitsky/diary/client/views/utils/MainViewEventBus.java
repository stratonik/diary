package ru.abelitsky.diary.client.views.utils;

public interface MainViewEventBus {

	/** Добавляет задачу в очередь выполнения */
	void put(MainViewEventBusTask task);

	/** Запускает выполнение задач из очереди выполнения */
	void start();

	/** Приостанавливает выполнение задач из очереди выполнение */
	void stop();

}
