package ru.abelitsky.diary.client.views.utils;

import java.util.LinkedList;

public class MainViewEventBusImpl implements MainViewEventBus {

	private boolean isStopped;
	private LinkedList<MainViewEventBusTask> tasks = new LinkedList<MainViewEventBusTask>();

	private void processTasks() {
		if (!isStopped) {
			while (!tasks.isEmpty()) {
				tasks.poll().run(this);
				if (isStopped) {
					break;
				}
			}
		}
	}

	@Override
	public void put(MainViewEventBusTask task) {
		tasks.addLast(task);
		processTasks();
	}

	@Override
	public void start() {
		isStopped = false;
		processTasks();
	}

	@Override
	public void stop() {
		isStopped = true;
	}

}
