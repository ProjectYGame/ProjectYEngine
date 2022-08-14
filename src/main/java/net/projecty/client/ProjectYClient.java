package net.projecty.client;

import com.jogamp.newt.Screen;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;

public class ProjectYClient implements GLEventListener {
	public static void main(String[] args) {
		new ProjectYClient();
	}
	
	private ProjectYClient() {
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2GL3));
		GLWindow glWindow = GLWindow.create(caps);
		
		glWindow.setTitle("Project Y");
		glWindow.setSize(800, 600);
		glWindow.setUndecorated(false);
		glWindow.setPointerVisible(true);
		glWindow.setVisible(true);
		
		Screen screen = glWindow.getScreen();
		int posX = (screen.getWidth() - glWindow.getWidth()) >> 1;
		int posY = (screen.getHeight() - glWindow.getHeight()) >> 1;
		glWindow.setPosition(posX, posY);
		
		glWindow.addGLEventListener(this);
		Animator animator = new Animator();
		animator.add(glWindow);
		animator.start();
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {}
	
	@Override
	public void display(GLAutoDrawable drawable) {}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		System.exit(0);
	}
}
