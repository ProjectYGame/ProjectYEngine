package net.projecty.client.rendering.shaders.uniforms;

import com.jogamp.opengl.GLContext;
import net.projecty.client.rendering.textures.Texture2D;

public class TextureUniform extends Uniform {
	private Texture2D texture;
	
	public TextureUniform(int id) {
		super(id);
		GLContext.getCurrentGL().getGL2ES2().glUniform1i(id, id);
	}
	
	public void setTexture(Texture2D texture) {
		this.texture = texture;
	}
	
	public Texture2D getTexture() {
		return texture;
	}
	
	public void bind() {
		if (getID() >= 0) {
			GLContext.getCurrentGL().getGL2ES2().glActiveTexture(GLContext.getCurrentGL().getGL2ES2().GL_TEXTURE0 + getID());
			texture.bind();
		}
	}
}
