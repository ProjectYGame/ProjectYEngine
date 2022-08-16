package net.projecty.core.world.block.state;

import net.projecty.core.world.block.Block;
import net.projecty.core.world.block.state.properties.StateProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class BlockState {
	private static final Map<Block, BlockState[]> POSSIBLE_STATES = new HashMap<>();
	private static int increment;
	private static int index;
	
	private final Map<StateProperty<?>, Object> properties = new HashMap<>();
	private final BlockState[] localCache;
	private final Block block;
	
	private BlockState(Block block) {
		this.localCache = POSSIBLE_STATES.computeIfAbsent(block, key -> {
			List<StateProperty> properties = new ArrayList<>();
			block.appendProperties(properties);
			int size = 1;
			for (StateProperty property: properties) size *= property.getCount();
			block.setDefaultState(this);
			properties.forEach(property -> this.properties.put(property, property.defaultValue()));
			BlockState[] cache = new BlockState[size];
			cache[0] = this;
			return cache;
		});
		this.block = block;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public <T> BlockState with(StateProperty<T> property, T value) {
		if (!properties.containsKey(property)) {
			throw new RuntimeException("No such property: " + property + " in block " + block);
		}
		
		index = 0;
		increment = 1;
		properties.forEach((prop, obj) -> {
			if (prop == property) index += property.getIndex(value) * increment;
			else index += prop.getCastedIndex(obj) * increment;
			increment *= prop.getCount();
		});
		
		BlockState state = localCache[index];
		if (state == null) {
			state = new BlockState(block);
			state.properties.putAll(properties);
			state.properties.put(property, value);
			localCache[index] = state;
		}
		
		return state;
	}
	
	public List<BlockState> getPossibleStates() {
		for (int i = 0; i < localCache.length; i++) {
			if (localCache[i] == null) {
				index = i;
				increment = 1;
				Map<StateProperty<?>, Object> newProperties = new HashMap<>();
				properties.keySet().forEach(prop -> {
					int indexInternal = (index / increment) % prop.getCount();
					newProperties.put(prop, prop.getValues().get(indexInternal));
					increment *= prop.getCount();
				});
				
				BlockState state = localCache[index];
				if (state == null) {
					state = new BlockState(block);
					state.properties.putAll(newProperties);
					localCache[i] = state;
				}
			}
		}
		
		return List.of(localCache);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		index = 0;
		final int max = properties.size();
		properties.forEach((prop, obj) -> {
			index++;
			builder.append(prop);
			builder.append("=");
			builder.append(obj);
			if (index < max) {
				builder.append(",");
			}
		});
		builder.append("]");
		return builder.toString();
	}
	
	public static BlockState getDefaultState(Block block) {
		if (POSSIBLE_STATES.containsKey(block)) return POSSIBLE_STATES.get(block)[0];
		return new BlockState(block);
	}
}
