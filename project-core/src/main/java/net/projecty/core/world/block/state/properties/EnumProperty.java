package net.projecty.core.world.block.state.properties;

import java.util.List;

public class EnumProperty <T extends Enum<T>> extends StateProperty<T> {
	private static final String TYPE = "enum";
	final List<T> values;
	
	public EnumProperty(String name, Class<T> enumClass) {
		super(name);
		this.values = List.of(enumClass.getEnumConstants());
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
