package net.projecty.client.rendering.gui;

import java.util.ArrayList;
import java.util.List;

public abstract class GUIScreen implements GUIElement {
	private final List<GUIElement> elements = new ArrayList<>();
	private final GUIScreen parent;
	
	public GUIScreen(GUIScreen parent) {
		this.parent = parent;
	}
	
	public void addElement(GUIElement element) {
		elements.add(element);
	}
	
	@Override
	public void render(long time, float delta) {
		renderBackground(time, delta);
		elements.forEach(element -> element.render(time, delta));
	}
	
	@Override
	public void onResize(int width, int height) {
		elements.forEach(element -> element.onResize(width, height));
	}
	
	@Override
	public void onClick(int x, int y) {
		elements.forEach(element -> element.onClick(x, y));
	}
	
	abstract void renderBackground(long time, float delta);
	
	protected void goToParent() {
	
	}
}
