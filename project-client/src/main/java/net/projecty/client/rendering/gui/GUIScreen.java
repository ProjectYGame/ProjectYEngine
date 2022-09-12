package net.projecty.client.rendering.gui;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.awt.TextRenderer;
import net.projecty.client.ProjectYClient;
import net.projecty.client.utils.GLUtil;
import net.projecty.core.math.vector.Vec2I;

import java.util.ArrayList;
import java.util.List;

public abstract class GUIScreen implements GUIElement {
	protected static final Vec2I CENTER = new Vec2I();
	protected static final Vec2I SIZE = new Vec2I();
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
		debug(delta);
	}
	
	@Override
	public void onResize(GLAutoDrawable drawable, int width, int height) {
		SIZE.set(width, height);
		CENTER.set(SIZE).divide(2);
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
	
	public int getWidth() {
		return SIZE.x;
	}
	
	public int getHeight() {
		return SIZE.y;
	}
	
	private void debug(float delta) {
		ProjectYClient client = ProjectYClient.getInstance();
		TextRenderer text = GLUtil.getDefaultText();
		text.beginRendering(SIZE.x, SIZE.y);
		text.draw("FPS: " + client.getAverageFPS(), 10, SIZE.y - 20);
		text.draw("Delta: " + client.getAverageDelta(), 10, SIZE.y - 40);
		text.endRendering();
	}
}
