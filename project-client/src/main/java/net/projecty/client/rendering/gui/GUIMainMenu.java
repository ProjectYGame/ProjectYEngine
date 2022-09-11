package net.projecty.client.rendering.gui;

import net.projecty.core.utils.LogUtil;

public class GUIMainMenu extends GUIScreen {
	public GUIMainMenu() {
		super(null);
	}
	
	@Override
	void renderBackground(long time, float delta) {
		LogUtil.debug(time);
	}
}
