package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;

public class BooleanUniform extends Uniform {
	private boolean value;
	
	public BooleanUniform(int id) {
		super(id);
	}
	
	public void setValue(boolean value) {
		this.value = value;
	}
	
	@Override
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUniform1i(getID(), value ? 1 : 0);
	}
}
