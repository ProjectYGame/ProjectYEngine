package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;
import net.projecty.core.math.matrix.Matrix4x4;

public class MatrixUniform extends Uniform {
	private Matrix4x4 matrix;
	
	public MatrixUniform(int id) {
		super(id);
	}
	
	public void setMatrix(Matrix4x4 matrix) {
		this.matrix = matrix;
	}
	
	@Override
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUniformMatrix4fv(getID(), 1, false, matrix.getBuffer());
	}
}
