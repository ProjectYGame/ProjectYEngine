package net.projecty.client.rendering.rendering.textures;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLContext;
import net.projecty.client.utils.Disposable;
import net.projecty.client.utils.DisposeUtil;

import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Texture2D implements Disposable {
	private final TextureType type;
	private final int textureID;
	
	private int height;
	private int width;
	
	/**
	 * Create new texture from {@link BufferedImage}.
	 * @param image source.
	 */
	public Texture2D(BufferedImage image) {
		this(TextureType.RGBA);
		height = image.getHeight();
		width = image.getWidth();
		setTexture(image);
		setFilter(TextureFilter.NEAREST);
		setWrapMode(TextureWrapMode.CLAMP);
		DisposeUtil.addObject(this);
	}
	
	/**
	 * Create new empty texture with specified size.
	 * @param width texture width (X axis).
	 * @param height texture height (Y axis).
	 * @param type {@link TextureType}.
	 */
	public Texture2D(int width, int height, TextureType type) {
		this(type);
		this.height = height;
		this.width = width;
		bind();
		setFilter(TextureFilter.NEAREST);
		setWrapMode(TextureWrapMode.CLAMP);
		update(ByteBuffer.allocateDirect(type.getCapacity(width * height)));
	}
	
	private Texture2D(TextureType type) {
		int[] temp = new int[1];
		GLContext.getCurrentGL().glGenTextures(1, temp, 0);
		textureID = temp[0];
		this.type = type;
		DisposeUtil.addObject(this);
	}
	
	/**
	 * Get current texture ID.
	 */
	public int getID() {
		return textureID;
	}
	
	/**
	 * Get texture width (X axis).
	 */
	public int getWidth() {
		return width;
	}
	
	/**
	 * Get texture height (Y axis).
	 */
	public int getHeight() {
		return height;
	}
	
	/**
	 * Change texture size. Texture data will be lost.
	 * @param width new texture width (X axis).
	 * @param height new texture height (Y axis).
	 */
	public void resize(int width, int height) {
		this.height = height;
		this.width = width;
		bind();
		update(ByteBuffer.allocateDirect(width * height));
	}
	
	/**
	 * Set texture data from {@link BufferedImage}. Width and height should be same as the texture have.
	 * @param image {@link BufferedImage} to set as texture.
	 */
	public void setTexture(BufferedImage image) {
		int[] data = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, image.getWidth());
		setTexture(data);
	}
	
	/**
	 * Set texture data. Buffer data should be same size as the texture.
	 * @param data integer array data to set.
	 */
	public void setTexture(int[] data) {
		bind();
		update(wrapBuffer(data));
	}
	
	/**
	 * Set texture data. Buffer data should be same size as the texture.
	 * @param data byte array data to set.
	 */
	public void setTexture(byte[] data) {
		bind();
		update(wrapBuffer(data));
	}
	
	/**
	 * Set area of texture from {@link BufferedImage}. Width and height are taken from image.
	 * @param image source {@link BufferedImage}.
	 * @param x image X position.
	 * @param y image Y position.
	 */
	public void setArea(BufferedImage image, int x, int y) {
		int[] data = new int[image.getWidth() * image.getHeight()];
		image.getRGB(0, 0, image.getWidth(), image.getHeight(), data, 0, image.getWidth());
		setArea(data, x, y, image.getWidth(), image.getHeight());
	}
	
	/**
	 * Set area of texture using integer data array.
	 * @param data source data.
	 * @param x image X position.
	 * @param y image Y position.
	 * @param width width of area (X axis).
	 * @param height height of area (Y axis).
	 */
	public void setArea(int[] data, int x, int y, int width, int height) {
		bind();
		type.genSubImage(x, y, width, height, wrapBuffer(data));
	}
	
	/**
	 * Set area of texture using byte data array.
	 * @param data source data.
	 * @param x image X position.
	 * @param y image Y position.
	 * @param width width of area (X axis).
	 * @param height height of area (Y axis).
	 */
	public void setArea(byte[] data, int x, int y, int width, int height) {
		bind();
		type.genSubImage(x, y, width, height, wrapBuffer(data));
	}
	
	/**
	 * Set texture wrap mode.
	 * @param mode {@link TextureWrapMode}.
	 */
	public void setWrapMode(TextureWrapMode mode) {
		GL gl = GLContext.getCurrentGL();
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_S, mode.getID());
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_WRAP_T, mode.getID());
	}
	
	/**
	 * Set texture filter.
	 * @param filter {@link TextureFilter}.
	 */
	public void setFilter(TextureFilter filter) {
		GL gl = GLContext.getCurrentGL();
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, filter.getID());
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, filter.getID());
	}
	
	/**
	 * Add automatic mipmaps to the texture.
	 */
	public void addMipMaps() {
		GL gl = GLContext.getCurrentGL();
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL2.GL_GENERATE_MIPMAP, GL.GL_TRUE);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MIN_FILTER, GL.GL_LINEAR_MIPMAP_LINEAR);
		gl.glTexParameteri(GL.GL_TEXTURE_2D, GL.GL_TEXTURE_MAG_FILTER, GL.GL_LINEAR);
	}
	
	/**
	 * Update texture data from buffer.
	 * @param buffer {@link ByteBuffer} to update data.
	 */
	private void update(ByteBuffer buffer) {
		bind();
		type.genTexture(width, height, buffer);
	}
	
	/**
	 * Bind current texture to GL_TEXTURE_2D.
	 */
	public void bind() {
		GLContext.getCurrentGL().glBindTexture(GL.GL_TEXTURE_2D, textureID);
	}
	
	@Override
	public void dispose() {
		GLContext.getCurrentGL().glDeleteTextures(1, new int[] {textureID}, 0);
	}
	
	/**
	 * Wraps integer data array into buffer.
	 * @param data array to wrap.
	 * @return wrapped {@link ByteBuffer}.
	 */
	private ByteBuffer wrapBuffer(int[] data) {
		ByteBuffer buffer = initBuffer(data.length);
		for (int i = 0; i < data.length; i++) {
			buffer.putInt(data[i]);
		}
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Wraps integer data array into buffer.
	 * @param data array to wrap.
	 * @return wrapped {@link ByteBuffer}.
	 */
	private ByteBuffer wrapBuffer(byte[] data) {
		ByteBuffer buffer = initBuffer(data.length);
		buffer.put(data);
		buffer.flip();
		return buffer;
	}
	
	/**
	 * Initialize correct {@link ByteBuffer} for textures.
	 * @param size buffer size.
	 */
	private ByteBuffer initBuffer(int size) {
		int capacity = type.getCapacity(size);
		ByteBuffer buffer = ByteBuffer.allocateDirect(capacity);
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		return buffer;
	}
	
	/**
	 * Unbind any texture from GL_TEXTURE_2D (set texture address to zero).
	 */
	public static void unbind() {
		GLContext.getCurrentGL().glBindTexture(GL.GL_TEXTURE_2D, 0);
	}
}