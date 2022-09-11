package net.projecty.client.rendering.shaders;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLContext;
import net.projecty.client.rendering.shaders.uniforms.Uniform;
import net.projecty.client.utils.Disposable;
import net.projecty.client.utils.DisposeUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class ShaderProgram implements Disposable {
	/*private Map<String, Uniform> uniforms = new HashMap<>();
	private final Shader[] shaders;
	private final int id;
	
	public ShaderProgram(Shader... shaders) {
		this.shaders = shaders;
		id = GL3.glCreateProgram();
		for (Shader shader: shaders) {
			GL3.glAttachShader(id, shader.getID());
		}
		GL3.glLinkProgram(id);
		if (GL3.glGetShaderi(id, GL3.GL_LINK_STATUS) == GL3.GL_FALSE) {
			throw new RuntimeException("Can't link shader program, reason: " + GL3.glGetShaderInfoLog(id, 512));
		}
		GL3.glValidateProgram(id);
		if (GL3.glGetShaderi(id, GL3.GL_VALIDATE_STATUS) == GL3.GL_FALSE) {
			throw new RuntimeException("Can't validate shader program, reason: " + GL3.glGetShaderInfoLog(id, 512));
		}
		DisposeUtil.addObject(this);
		unbind();
	}
	
	public <T extends Uniform> T getUniform(String name, Function<Integer, T> constructor) {
		Uniform uniform = uniforms.get(name);
		if (uniform != null) {
			return (T) uniform;
		}
		this.bind();
		int id = GLContext.getCurrentGL().getGL2ES2().glGetUniformLocation(this.id, name);
		T result = constructor.apply(id);
		uniforms.put(name, result);
		return result;
	}
	
	public void bind() {
		GLContext.getCurrentGL().getGL2ES2().glUseProgram(id);
	}
	
	public void bindWithUniforms() {
		bind();
		uniforms.values().forEach(uniform -> uniform.bind());
	}
	
	public static void unbind() {
		GLContext.getCurrentGL().getGL2ES2().glUseProgram(0);
	}*/
	
	@Override
	public void dispose() {
		/*for (Shader shader: shaders) {
			shader.dispose();
		}
		GLContext.getCurrentGL().getGL2ES2().glDeleteProgram(id);*/
	}
}
