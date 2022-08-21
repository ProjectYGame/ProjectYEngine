package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;
import net.projecty.core.math.vector.Vec2F;

public class Vec2FUniform extends Uniform {
	private Vec2F vector = new Vec2F();
	
	public Vec2FUniform(int id) {
		super(id);
	}
	
	public Vec2F getVector() {
		return vector;
	}
	
	public void set(float x, float y) {
		vector.set(x, y);
	}
	
	@Override
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUniform2f(getID(), vector.x, vector.y);
	}
}
