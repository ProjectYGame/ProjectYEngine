package net.projecty.core.world.node.registry;

import net.projecty.core.world.item.Item;
import net.projecty.core.world.node.GameNode;
import net.projecty.core.world.node.state.NodeState;

public class DefaultRegistries {
	public static final Registry<GameNode> NODE_REGISTRY = new Registry<>();
	public static final Registry<Item> ITEM_REGISTRY = new Registry<>();
	
	public static final SerialisationMap<NodeState> NODESTATES_MAP = new SerialisationMap<>(
		"nodestates",
		NodeState::toNBTString,
		NodeState::fromNBTString
	);
}
