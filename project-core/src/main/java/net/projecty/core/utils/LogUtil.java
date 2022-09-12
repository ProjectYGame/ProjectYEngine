package net.projecty.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogUtil {
	private static final Logger LOGGER = LogManager.getLogger("project_y");
	
	public static Logger getLogger() {
		return LOGGER;
	}
	
	private static String toString(Object arg) {
		return arg == null ? "null" : arg.toString();
	}
	
	public static void info(Object arg) {
		String message = toString(arg);
		LOGGER.info(message);
		LOGGER.debug(message);
	}
	
	public static void info(Object... args) {
		for (Object obj: args) {
			info(obj);
		}
	}
	
	public static void stackTrace() {
		warn(System.err);
	}
	
	public static void warn(Object arg) {
		String message = toString(arg);
		LOGGER.warn(message);
		LOGGER.debug(message);
	}
	
	public static void warn(Object... args) {
		for (Object obj: args) {
			warn(obj);
		}
	}
	
	public static void exception(String message) {
		info(message);
		throw new RuntimeException(message);
	}
	
	public static void debug(Object arg) {
		LOGGER.debug(toString(arg));
	}
	
	public static void debug(Object... args) {
		for (Object obj: args) {
			debug(obj);
		}
	}
}
