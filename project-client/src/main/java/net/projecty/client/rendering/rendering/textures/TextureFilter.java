package net.projecty.client.rendering.rendering.textures;

import com.jogamp.opengl.GL;

/**
 * Texture filter enum, used to specify how texture will look like during rendering.
 * <p><b style="color:#579bf4;">{@code LINEAR}</b> - will result with blurry pixels (linear interpolation).</p>
 * <p><b style="color:#579bf4;">{@code NEAREST}</b> - will result with hard pixels.</p>
 */
public enum TextureFilter {
	LINEAR(GL.GL_LINEAR),
	NEAREST(GL.GL_NEAREST);
	
	private final int id;
	
	TextureFilter(int id) {
		this.id = id;
	}
	
	public int getID() {
		return id;
	}
}
