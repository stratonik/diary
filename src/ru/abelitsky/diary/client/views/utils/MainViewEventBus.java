package ru.abelitsky.diary.client.views.utils;

public interface MainViewEventBus {

	/** Добавляет задачу в очередь выполнения */
	void add(MainViewEventBusTask task);
	
	/** Удаляет все задачи из очереди выполнения и отменяет состояние "Приостановлен" */
	void reset();

	/** Запускает выполнение задач из очереди выполнения */
	void start();

	/** Приостанавливает выполнение задач из очереди выполнение */
	void stop();

}
