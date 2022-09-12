package net.projecty.client;

import com.jogamp.newt.Screen;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;
import net.projecty.client.rendering.gui.GUIMainMenu;
import net.projecty.client.rendering.gui.GUIScreen;
import net.projecty.client.utils.DisposeUtil;
import net.projecty.core.utils.LogUtil;
import net.projecty.core.utils.MathUtil;

public class ProjectYClient implements GLEventListener {
	private static ProjectYClient instance;
	private GUIScreen screen;
	private float delta;
	private long time;
	
	private byte averageTicks;
	private short averageFPS;
	private float averageDelta;
	private float sumDelta;
	
	public static void main(String[] args) {
		new ProjectYClient();
	}

	private ProjectYClient() {
		LogUtil.debug("ProjectYClient::construct() - START");
		
		System.setProperty("newt.window.icons", "icons/icon-32.png,icons/icon-64.png,icons/icon-128.png");
		instance = this;
		
		GLCapabilities caps = new GLCapabilities(GLProfile.get(GLProfile.GL2GL3));
		GLWindow glWindow = GLWindow.create(caps);

		glWindow.setTitle("Project Y");
		glWindow.setSize(960, 540);
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
		
		LogUtil.debug("ProjectYClient::construct() - END");
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		screen = new GUIMainMenu();
		time = System.currentTimeMillis();
		//drawable.getGL().setSwapInterval(1); // V-Sync
		drawable.getGL().setSwapInterval(0);
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		screen.render(drawable, time, delta);
		updateTime();
		updateAverage();
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		screen.onResize(drawable, width, height);
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		LogUtil.debug("ProjectYClient::dispose() - STOP APP");
		DisposeUtil.disposeAll();
		System.exit(0);
	}
	
	private void updateTime() {
		long t = System.currentTimeMillis();
		delta = (float) (t - time) / 1000F;
		time = t;
	}
	
	private void updateAverage() {
		sumDelta += delta;
		if (averageTicks++ == 8) {
			averageDelta = sumDelta / averageTicks;
			averageFPS = (short) MathUtil.round(1.0F / averageDelta);
			averageTicks = 0;
			sumDelta = 0;
		}
	}
	
	public short getAverageFPS() {
		return averageFPS;
	}
	
	public float getAverageDelta() {
		return delta;
	}
	
	public static ProjectYClient getInstance() {
		return instance;
	}
}