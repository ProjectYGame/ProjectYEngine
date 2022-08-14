package net.projecty.common.math.storage;

import java.util.Arrays;

public class ImmutableArray <T> {
	private final T[] data;
	public final int length;
	
	public ImmutableArray(T... data) {
		this.data = data;
		this.length = data.length;
	}
	
	public T get(int index) {
		return data[index];
	}
	
	public T[] mutableCopy() {
		return Arrays.copyOf(data, length);
	}
}
