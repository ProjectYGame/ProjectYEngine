package net.projecty.client.rendering.shaders;

import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLContext;
import net.projecty.client.utils.Disposable;
import net.projecty.client.utils.DisposeUtil;

public class Shader implements Disposable {
	private final ShaderType type;
	private final int id;
	
	public Shader(String shaderCode, ShaderType type) {
		this.type = type;
		id = GLContext.getCurrentGL().getGL2ES2().glCreateShader(type.getID());
		GLContext.getCurrentGL().getGL2ES2().glShaderSource(id, shaderCode);
		GLContext.getCurrentGL().getGL2ES2().glCompileShader(id);
		if (GLContext.getCurrentGL().getGL2ES2().glGetShaderInfoLog(id, GL3.GL_COMPILE_STATUS) == GL3.GL_FALSE) {
			throw new RuntimeException("Can't create shader with type " + type + ", reason: " + GL20.glGetShaderInfoLog(id, 512));
		}
		DisposeUtil.addObject(this);
	}
	
	public ShaderType getType() {
		return type;
	}
	
	public int getID() {
		return id;
	}
	
	@Override
	public void dispose() {
		GL20.glDeleteShader(id);
	}
	
	public static Shader create(Identifier id, ShaderType type) {
		String path = "/assets/" + id.modID + "/shaders/" + id.id + "." + type.getExtension();
		String source = ResourceUtil.readResourceAsString(path, true, true, true);
		return new Shader(source, type);
	}
}
