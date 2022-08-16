package net.projecty.world.block;

import net.projecty.world.block.state.BlockState;
import net.projecty.world.block.state.properties.StateProperty;

import java.util.Collection;
import java.util.List;

public class Block {
	private BlockState defaultState;
	
	public Block() {
		setDefaultState(BlockState.getDefaultState(this));
	}
	
	public void appendProperties(Collection<StateProperty> properties) {}
	
	public final BlockState getDefaultState() {
		return defaultState;
	}
	
	public final List<BlockState> getPossibleStates() {
		return getDefaultState().getPossibleStates();
	}
	
	public final void setDefaultState(BlockState defaultState) {
		if (defaultState.getBlock() != this) {
			throw new RuntimeException("Blockstate " + defaultState + " cannot be set to block " + this + " as the default state");
		}
		this.defaultState = defaultState;
	}
}
