package net.projecty.client.rendering.gui;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import net.projecty.core.math.vector.Vec2F;

import java.util.ArrayList;
import java.util.List;

public abstract class GUIScreen implements GUIElement {
	protected static final Vec2F CENTER = new Vec2F();
	protected static final Vec2F SIZE = new Vec2F();
	protected final List<GUIElement> elements = new ArrayList<>();
	protected final GUIScreen parent;
	
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
		SIZE.set(width, height);
		CENTER.set(SIZE).multiply(0.5F);
		initOrtho(drawable, width, height);
		elements.forEach(element -> element.onResize(drawable, width, height));
	}
	
	@Override
	public void onClick(int x, int y) {
		elements.forEach(element -> element.onClick(x, y));
	}
	
	abstract void renderBackground(GLAutoDrawable drawable, long time, float delta);
	
	protected void initOrtho(GLAutoDrawable drawable, int width, int height) {
		GL2 gl2 = drawable.getGL().getGL2();
		gl2.glViewport(0, 0, width, height);
		gl2.glMatrixMode(GL2.GL_PROJECTION);
		gl2.glLoadIdentity();
		gl2.glOrtho(0, width, height, 0, -10, 10);
		gl2.glMatrixMode(GL2.GL_MODELVIEW);
		gl2.glLoadIdentity();
	}
}
