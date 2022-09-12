package net.projecty.client.rendering.gui;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;

public interface GUIElement {
	void render(GLAutoDrawable drawable, long time, float delta);
	void onResize(GLAutoDrawable drawable, int width, int height);
	void onClick(int x, int y);
	
	default void blit(GLAutoDrawable drawable, float x, float y, float w, float h) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glBegin(GL3.GL_QUADS);
		gl2.glTexCoord2f(0.0F, 1.0F);
		gl2.glVertex2f(x, y);
		gl2.glTexCoord2f(0.0F, 0.0F);
		gl2.glVertex2f(x, y + h);
		gl2.glTexCoord2f(1.0F, 0.0F);
		gl2.glVertex2f(x + w, y + h);
		gl2.glTexCoord2f(1.0F, 1.0F);
		gl2.glVertex2f(x + w, y);
		gl2.glEnd();
	}
}
