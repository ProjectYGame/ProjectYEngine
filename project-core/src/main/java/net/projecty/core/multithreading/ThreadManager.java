package net.projecty.core.multithreading;

import net.projecty.core.utils.Identifier;

import java.util.HashMap;
import java.util.Map;

public class ThreadManager {
	private static final Map<Identifier, SimpleThread> THREADS = new HashMap<>();

	public static void createThread(Identifier threadID, Runnable threadedMethod, boolean loop) {
		if (THREADS.containsKey(threadID)) throw new RuntimeException("Thread with ID " + threadID + " already exists");
		SimpleThread thread = new SimpleThread(threadID, threadedMethod, loop);
		THREADS.put(threadID, thread);
		thread.start();
	}

	public static void stopThread(Identifier threadID) {
		SimpleThread thread = THREADS.get(threadID);
		if (thread != null) thread.canRun = false;
	}

	private static class SimpleThread extends Thread {
		final Runnable threadedMethod;
		boolean canRun = true;
		final boolean isLoop;
		final Identifier id;

		SimpleThread(Identifier id, Runnable threadedMethod, boolean isLoop) {
			this.threadedMethod = threadedMethod;
			this.isLoop = isLoop;
			this.id = id;
			this.setName(id.toString());
		}

		@Override
		public void run() {
			if (isLoop) {
				while (canRun) {
					threadedMethod.run();
				}
			}
			else {
				threadedMethod.run();
			}
			THREADS.remove(id);
		}
	}
}
