package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;

public class FloatUniform extends Uniform {
	protected float value;
	
	public FloatUniform(int id) {
		super(id);
	}
	
	public void setValue(float value) {
		this.value = value;
	}
	
	@Override
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUniform1f(getID(), value);
	}
}
