package net.projecty.client.utils;

import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import net.projecty.client.rendering.shaders.ShaderType;
import net.projecty.client.utils.disposable.GLDisposableContainer;
import net.projecty.core.utils.Identifier;

public class GLUtil {
	public static GL2ES2 getGL2ES2() {
		return GLContext.getCurrentGL().getGL2ES2();
	}
	
	public static ShaderProgram makeProgram() {
		ShaderProgram program = new ShaderProgram();
		DisposeUtil.addObject(new GLDisposableContainer(program::destroy));
		return program;
	}
	
	// TODO load shader code from resources
	public static ShaderCode loadShader(Identifier shaderLocation, ShaderType type) {
		String[][] source = new String[1][0];
		ShaderCode code = new ShaderCode(type.getID(), 1, source);
		DisposeUtil.addObject(new GLDisposableContainer(code::destroy));
		return code;
	}
}
