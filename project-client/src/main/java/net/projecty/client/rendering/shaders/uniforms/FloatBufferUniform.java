package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;
import net.projecty.core.utils.BufferUtil;

import java.nio.FloatBuffer;

public class FloatBufferUniform extends Uniform {
	protected FloatBuffer buffer;
	protected int size;
	
	public FloatBufferUniform(int id) {
		super(id);
	}
	
	public void setValues(float[] values) {
		if (buffer == null || size != values.length) {
			buffer = BufferUtil.createFloatBuffer(values.length);
			size = values.length;
		}
		buffer.put(values);
		buffer.flip();
	}
	
	@Override
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUniform1fv(getID(), size, buffer);
	}
}
