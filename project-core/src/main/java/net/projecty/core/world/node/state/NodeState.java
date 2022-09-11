package net.projecty.core.world.node.state;

import net.projecty.core.utils.Identifier;
import net.projecty.core.world.node.GameNode;
import net.projecty.core.world.node.registry.DefaultRegistries;
import net.projecty.core.world.node.state.properties.StateProperty;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NodeState {
	private static final Map<GameNode, NodeState[]> POSSIBLE_STATES = new HashMap<>();
	
	private static int increment;
	private static int index;
	
	private final Map<StateProperty<?>, Object> properties = new HashMap<>();
	private final NodeState[] localCache;
	private final GameNode node;
	
	private NodeState(GameNode node) {
		this.localCache = POSSIBLE_STATES.computeIfAbsent(node, key -> {
			List<StateProperty> properties = new ArrayList<>();
			node.appendProperties(properties);
			int size = 1;
			for (StateProperty property: properties) size *= property.getCount();
			node.setDefaultState(this);
			properties.forEach(property -> this.properties.put(property, property.defaultValue()));
			NodeState[] cache = new NodeState[size];
			DefaultRegistries.NODESTATES_MAP.add(this);
			cache[0] = this;
			return cache;
		});
		this.node = node;
	}
	
	public <T> NodeState with(StateProperty<T> property, T value) {
		if (!properties.containsKey(property)) throw new RuntimeException("No property " + property + " in node " + node);
		
		index = 0;
		increment = 1;
		properties.forEach((prop, obj) -> {
			if (prop == property) index += property.getIndex(value) * increment;
			else index += prop.getCastedIndex(obj) * increment;
			increment *= prop.getCount();
		});
		
		NodeState state = localCache[index];
		if (state == null) {
			state = new NodeState(node);
			state.properties.putAll(properties);
			state.properties.put(property, value);
			localCache[index] = state;
			DefaultRegistries.NODESTATES_MAP.add(state);
		}
		
		return state;
	}
	
	public GameNode getBlock() {
		return node;
	}
	
	public static NodeState getDefaultState(GameNode node) {
		if (POSSIBLE_STATES.containsKey(node)) return POSSIBLE_STATES.get(node)[0];
		return new NodeState(node);
	}
	
	public List<NodeState> getPossibleStates() {
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
				
				NodeState state = localCache[index];
				if (state == null) {
					state = new NodeState(node);
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
		Identifier nodeID = DefaultRegistries.NODE_REGISTRY.getID(node);
		if (nodeID == null) {
			throw new RuntimeException("Block " + node + " is not in node registry!");
		}
		StringBuilder builder = new StringBuilder("node=");
		builder.append(nodeID);
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
	public static NodeState fromNBTString(String nbtString) {
		String[] parts = nbtString.split(",");
		String name = parts[0];
		GameNode node = DefaultRegistries.NODE_REGISTRY.get(Identifier.make(name.substring(6)));
		if (node == null) return null;
		NodeState state = getDefaultState(node);
		for (int i = 1; i < parts.length; i++) {
			String[] pair = parts[i].split("=");
			StateProperty<?> property = state.getByName(pair[0]);
			if (property != null) {
				state = state.withCast(property, property.parseValue(pair[1]));
			}
		}
		return state;
	}
	
	private <T> NodeState withCast(StateProperty<T> property, Object value) {
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
