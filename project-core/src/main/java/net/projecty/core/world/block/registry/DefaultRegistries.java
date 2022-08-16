package net.projecty.core.world.block.registry;

import net.projecty.core.world.block.Block;
import net.projecty.core.world.block.state.BlockState;
import net.projecty.core.world.item.Item;

public class DefaultRegistries {
	public static final Registry<Block> BLOCK_REGISTRY = new Registry<>();
	public static final Registry<Item> ITEM_REGISTRY = new Registry<>();
	
	public static final SerialisationMap<BlockState> BLOCKSTATES_MAP = new SerialisationMap<>(
		"blockstates",
		BlockState::toNBTString,
		BlockState::fromNBTString
	);
}
