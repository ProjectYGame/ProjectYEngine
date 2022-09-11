package net.projecty.core.world.node.state;

import net.projecty.core.math.vector.Vec3I;

public interface NodeStateHolder {
	/**
	 * Set node state, return {@code true} on success
	 * @param pos {@link Vec3I} state position
	 * @param state {@link NodeState} to set
	 * @param updateDepth depth of the recursive node updates
	 */
	boolean setNodeState(Vec3I pos, NodeState state, int updateDepth);
	
	/**
	 * Get {@link NodeState} from specified position
	 * @param pos {@link Vec3I} state position
	 */
	NodeState getNodeState(Vec3I pos);
	
	/**
	 * Set node state, return {@code true} on success. Will have update depth = 16
	 * @param pos {@link Vec3I} state position
	 * @param state {@link NodeState} to set
	 */
	default boolean setNodeState(Vec3I pos, NodeState state) {
		return setNodeState(pos, state, 16);
	}
}
