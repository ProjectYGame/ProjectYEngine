package net.projecty.core.world.block.state;

import net.projecty.core.utils.Identifier;
import net.projecty.core.world.block.Block;
import net.projecty.core.world.block.registry.DefaultRegistries;
import net.projecty.core.world.block.state.properties.StateProperty;

import javax.annotation.Nullable;
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
			DefaultRegistries.BLOCKSTATES_MAP.add(this);
			cache[0] = this;
			return cache;
		});
		this.block = block;
	}
	
	public <T> BlockState with(StateProperty<T> property, T value) {
		if (!properties.containsKey(property)) throw new RuntimeException("No property " + property + " in block " + block);
		
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
			DefaultRegistries.BLOCKSTATES_MAP.add(state);
		}
		
		return state;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public static BlockState getDefaultState(Block block) {
		if (POSSIBLE_STATES.containsKey(block)) return POSSIBLE_STATES.get(block)[0];
		return new BlockState(block);
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
		return toNBTString();
	}
	
	public String toNBTString() {
		Identifier blockID = DefaultRegistries.BLOCK_REGISTRY.getID(block);
		if (blockID == null) {
			throw new RuntimeException("Block " + block + " is not in block registry!");
		}
		StringBuilder builder = new StringBuilder("block=");
		builder.append(blockID);
		index = 0;
		final int max = properties.size();
		if (max > 0) {
			builder.append(",");
			properties.forEach((prop, obj) -> {
				index++;
				builder.append(prop.getName());
				builder.append("=");
				builder.append(prop.toString(obj));
				if (index < max) {
					builder.append(",");
				}
			});
		}
		return builder.toString();
	}
	
	@Nullable
	public static BlockState fromNBTString(String nbtString) {
		String[] parts = nbtString.split(",");
		String name = parts[0];
		Block block = DefaultRegistries.BLOCK_REGISTRY.get(Identifier.make(name.substring(6)));
		if (block == null) return null;
		BlockState state = getDefaultState(block);
		for (int i = 1; i < parts.length; i++) {
			String[] pair = parts[i].split("=");
			StateProperty<?> property = state.getByName(pair[0]);
			if (property != null) {
				state = state.withCast(property, property.parseValue(pair[1]));
			}
		}
		return state;
	}
	
	private <T> BlockState withCast(StateProperty<T> property, Object value) {
		return with(property, (T) value);
	}
	
	private StateProperty<?> getByName(String name) {
		for (StateProperty<?> property: properties.keySet()) {
			if (property.getName().equals(name)) {
				return property;
			}
		}
		return null;
	}
}
