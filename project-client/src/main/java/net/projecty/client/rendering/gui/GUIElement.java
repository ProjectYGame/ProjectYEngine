package net.projecty.client.rendering.gui;

import com.jogamp.opengl.GLAutoDrawable;

public interface GUIElement {
	void render(GLAutoDrawable drawable, long time, float delta);
	void onResize(GLAutoDrawable drawable, int width, int height);
	void onClick(int x, int y);
}
