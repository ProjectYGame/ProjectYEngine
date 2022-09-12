package net.projecty.core.world.node;

import net.projecty.core.registry.DefaultRegistries;
import net.projecty.core.utils.Identifier;
import net.projecty.core.utils.LogUtil;
import net.projecty.core.world.node.state.NodeState;
import net.projecty.core.world.node.state.properties.StateProperty;

import java.util.Collection;
import java.util.List;

public abstract class GameNode {
	private NodeState defaultState;
	
	public GameNode() {
		setDefaultState(NodeState.getDefaultState(this));
	}
	
	public void appendProperties(Collection<StateProperty> properties) {}
	
	public final NodeState getDefaultState() {
		return defaultState;
	}
	
	public final List<NodeState> getPossibleStates() {
		return getDefaultState().getPossibleStates();
	}
	
	public final void setDefaultState(NodeState defaultState) {
		if (defaultState.getBlock() != this) {
			StringBuilder builder = new StringBuilder("NodeState ");
			builder.append(defaultState);
			builder.append(" cannot be set to GameNode ");
			builder.append(this);
			builder.append(" as the default state");
			LogUtil.exception(builder.toString());
		}
		this.defaultState = defaultState;
	}
	
	@Override
	public String toString() {
		Identifier id = DefaultRegistries.NODE_REGISTRY.getID(this);
		return id == null ? super.toString() + " (not in registry)" : id.toString();
	}
}
