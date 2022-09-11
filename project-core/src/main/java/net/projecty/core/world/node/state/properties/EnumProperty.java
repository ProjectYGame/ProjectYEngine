package net.projecty.core.world.node.state.properties;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import java.util.List;

public class EnumProperty <T extends Enum<T>> extends StateProperty<T> {
	private static final String TYPE = "enum";
	final ImmutableMap<String, T> pairs;
	final ImmutableList<T> values;
	
	public EnumProperty(String name, Class<T> enumClass) {
		super(name);
		this.values = ImmutableList.copyOf(enumClass.getEnumConstants());
		Builder<String, T> builder = ImmutableMap.builder();
		this.values.forEach(value -> builder.put(value.name(), value));
		this.pairs = builder.build();
	}
	
	@Override
	public T parseValue(String value) {
		return pairs.get(value);
	}
	
	@Override
	public String toString(Object value) {
		return ((T) value).name();
	}
	
	@Override
	public List<T> getValues() {
		return values;
	}
	
	@Override
	public String getType() {
		return TYPE;
	}
	
	@Override
	public T defaultValue() {
		return values.get(0);
	}
	
	@Override
	public int getCount() {
		return values.size();
	}
	
	@Override
	public int getIndex(T value) {
		return value.ordinal();
	}
}
