package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;
import net.projecty.core.utils.BufferUtil;

import java.nio.IntBuffer;

public class Vec3IUniform extends Uniform {
	private IntBuffer buffer = BufferUtil.createIntBuffer(3);
	
	public Vec3IUniform(int id) {
		super(id);
	}
	
	public void set(int x, int y, int z) {
		buffer.put(x);
		buffer.put(y);
		buffer.put(z);
		buffer.rewind();
	}
	
	@Override
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUniform3iv(getID(), 1, buffer);
	}
}
