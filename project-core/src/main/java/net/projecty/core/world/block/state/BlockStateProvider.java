package net.projecty.core.world.block.state;

import net.projecty.core.math.vector.Vec3I;

public interface BlockStateProvider {
	/**
	 * Set block state, return {@code true} on success
	 * @param pos {@link Vec3I} state position
	 * @param state {@link BlockState} to set
	 * @param updateDepth depth of the recursive block updates
	 */
	boolean setBlockState(Vec3I pos, BlockState state, int updateDepth);
	
	/**
	 * Get {@link BlockState} from specified position
	 * @param pos {@link Vec3I} state position
	 */
	BlockState getBlockState(Vec3I pos);
	
	/**
	 * Set block state, return {@code true} on success. Will have update depth = 16
	 * @param pos {@link Vec3I} state position
	 * @param state {@link BlockState} to set
	 */
	default boolean setBlockState(Vec3I pos, BlockState state) {
		return setBlockState(pos, state, 16);
	}
}
