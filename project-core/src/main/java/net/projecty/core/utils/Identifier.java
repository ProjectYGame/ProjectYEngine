package net.projecty.core.utils;

import java.util.HashMap;
import java.util.Map;

public class Identifier {
	private static final Map<String, Identifier> CACHE = new HashMap<>();
	private final String complete;
	private final String root;
	private final String path;
	private final int hash;

	private Identifier(String root, String path) {
		this.complete = root + ":" + path;
		this.hash = complete.hashCode();
		this.root = root;
		this.path = path;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || !(obj instanceof Identifier)) return false;
		Identifier id = (Identifier) obj;
		return root.equals(id.root) && path.equals(id.path);
	}

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public String toString() {
		return complete;
	}

	public static Identifier make(String root, String path) {
		String key = root + ":" + path;
		synchronized (CACHE) {
			return CACHE.computeIfAbsent(key, (k) -> new Identifier(root, path));
		}
	}

	public static Identifier make(String path) {
		short index = (short) path.indexOf(':');
		if (path.indexOf(':') < 0) {
			path = "default:" + path;
			index = 7;
		};
		final String key = path;
		final short i = index;
		synchronized (CACHE) {
			return CACHE.computeIfAbsent(key, (k) -> new Identifier(key.substring(0, i), key.substring(i + 1)));
		}
	}
}
