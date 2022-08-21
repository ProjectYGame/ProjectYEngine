package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;

public class IntUniform extends Uniform {
	protected int value;
	
	public IntUniform(int id) {
		super(id);
	}
	
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUniform1i(getID(), value);
	}
}
