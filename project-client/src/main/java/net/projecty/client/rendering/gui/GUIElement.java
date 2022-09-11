package net.projecty.client.rendering.gui;

public interface GUIElement {
	void render(long time, float delta);
	void onResize(int width, int height);
	void onClick(int x, int y);
}
