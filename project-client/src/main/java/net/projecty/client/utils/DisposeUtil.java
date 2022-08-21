package net.projecty.client.utils;

import java.util.ArrayList;
import java.util.List;

public class DisposeUtil {
	private static final List<Disposable> OBJECTS = new ArrayList<>();
	
	public static void addObject(Disposable obj) {
		OBJECTS.add(obj);
	}
	
	public static void disposeAll() {
		OBJECTS.forEach(obj -> obj.dispose());
	}
}
