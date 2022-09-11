package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;
import net.projecty.core.utils.BufferUtil;

import java.nio.IntBuffer;

public class IntBufferUniform extends Uniform {
	protected IntBuffer buffer;
	protected int size;
	
	public IntBufferUniform(int id) {
		super(id);
	}
	
	public void setValues(int[] values) {
		if (buffer == null || size != values.length) {
			buffer = BufferUtil.createIntBuffer(values.length);
			size = values.length;
		}
		buffer.put(values);
		buffer.flip();
	}
	
	@Override
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUniform1iv(getID(), size, buffer);
	}
}
