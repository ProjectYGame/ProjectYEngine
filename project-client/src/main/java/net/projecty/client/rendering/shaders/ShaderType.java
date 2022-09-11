package net.projecty.client.rendering.shaders;

import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GL3;

public enum ShaderType {
	VERTEX(GL2ES2.GL_VERTEX_SHADER, "vert"),
	GEOMETRY(GL3.GL_GEOMETRY_SHADER, "geom"),
	FRAGMENT(GL2ES2.GL_FRAGMENT_SHADER, "frag");
	
	private final int id;
	private final String extension;
	
	ShaderType(int id, String extension) {
		this.id = id;
		this.extension = extension;
	}
	
	public int getID() {
		return id;
	}
	
	public String getExtension() {
		return extension;
	}
}
