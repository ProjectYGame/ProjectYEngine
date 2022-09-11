package net.projecty.client.rendering.shaders.uniforms;

public abstract class Uniform {
	private final int id;
	
	public Uniform(int id) {
		this.id = id;
	}
	
	public abstract void bind();
	
	public int getID() {
		return id;
	}
}
