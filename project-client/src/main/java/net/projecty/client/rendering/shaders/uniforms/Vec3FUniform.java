package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;
import net.projecty.core.math.vector.Vec3F;

public class Vec3FUniform extends Uniform {
	private Vec3F vector = new Vec3F();
	
	public Vec3FUniform(int id) {
		super(id);
	}
	
	public Vec3F getVector() {
		return vector;
	}
	
	public void set(float x, float y, float z) {
		vector.set(x, y, z);
	}
	
	@Override
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUniform3f(getID(), vector.x, vector.y, vector.z);
	}
}
