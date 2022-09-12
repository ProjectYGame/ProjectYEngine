package net.projecty.client.rendering.gui;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.glsl.ShaderProgram;

import java.util.ArrayList;
import java.util.List;

public abstract class GUIScreen implements GUIElement {
	protected static final ShaderProgram GUI_PROGRAM = new ShaderProgram();
	private final List<GUIElement> elements = new ArrayList<>();
	private final GUIScreen parent;
	
	public GUIScreen(GUIScreen parent) {
		this.parent = parent;
	}
	
	public void addElement(GUIElement element) {
		elements.add(element);
	}
	
	@Override
	public void render(GLAutoDrawable drawable, long time, float delta) {
		renderBackground(drawable, time, delta);
		elements.forEach(element -> element.render(drawable, time, delta));
	}
	
	@Override
	public void onResize(GLAutoDrawable drawable, int width, int height) {
		initOrtho(drawable, width, height);
		elements.forEach(element -> element.onResize(drawable, width, height));
	}
	
	@Override
	public void onClick(int x, int y) {
		elements.forEach(element -> element.onClick(x, y));
	}
	
	abstract void renderBackground(GLAutoDrawable drawable, long time, float delta);
	
	protected void goToParent() {
	
	}
	
	protected void initOrtho(GLAutoDrawable drawable, int width, int height) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glViewport(0, 0, width, height);
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		gl2.glOrtho(0, width, height, 0, -10, 10);
		//gl2.glOrtho(-width / 50F, width / 50F, height / 50F, -height / 50F, -10, 10);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
	}
	
	protected void blit(GLAutoDrawable drawable, float x, float y, float w, float h) {
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
