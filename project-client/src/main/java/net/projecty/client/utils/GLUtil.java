package net.projecty.client.utils;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLContext;
import com.jogamp.opengl.util.glsl.ShaderCode;
import com.jogamp.opengl.util.glsl.ShaderProgram;
import com.jogamp.opengl.util.texture.Texture;
import com.jogamp.opengl.util.texture.TextureData;
import com.jogamp.opengl.util.texture.TextureIO;
import net.projecty.client.rendering.shaders.ShaderType;
import net.projecty.client.utils.disposable.GLDisposableContainer;
import net.projecty.core.utils.BufferUtil;
import net.projecty.core.utils.Identifier;
import net.projecty.core.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;

public class GLUtil {
	private static final Texture EMPTY;
	
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
	
	public static Texture loadTexture(File file, boolean mipmaps) {
		Texture texture = null;
		try {
			texture = TextureIO.newTexture(file, mipmaps);
			DisposeUtil.addObject(new GLDisposableContainer(texture::destroy));
		}
		catch (IOException e) {
			LogUtil.stackTrace();
		}
		return texture;
	}
	
	public static Texture makeTexture(TextureData data) {
		Texture texture = new Texture(getGL2ES2(), data);
		DisposeUtil.addObject(new GLDisposableContainer(texture::destroy));
		return texture;
	}
	
	public static Texture getEmpty() {
		return EMPTY;
	}
	
	public static void setNearest(Texture texture) {
		texture.setTexParameteri(getGL2ES2(), GL2.GL_TEXTURE_MAG_FILTER, GL2.GL_NEAREST);
		texture.setTexParameteri(getGL2ES2(), GL2.GL_TEXTURE_MIN_FILTER, GL2.GL_NEAREST);
	}
	
	static {
		IntBuffer buffer = BufferUtil.createIntBuffer(4);
		buffer.put(0);
		buffer.put(0xff00ff);
		buffer.put(0xff00ff);
		buffer.put(0);
		buffer.rewind();
		
		TextureData data = new TextureData(
			GLContext.getCurrentGL().getGLProfile(),
			GL.GL_RGBA,
			2, 2, 0,
			GL.GL_RGBA,
			GL.GL_UNSIGNED_BYTE,
			false, false, false,
			buffer, null
		);
		EMPTY = new Texture(getGL2ES2(), data);
		setNearest(EMPTY);
	}
}
