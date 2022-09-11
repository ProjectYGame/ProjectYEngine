package net.projecty.client;

import com.jogamp.newt.Screen;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.Animator;
import net.projecty.client.game.ClientGame;
import net.projecty.client.rendering.gui.GUIMainMenu;
import net.projecty.client.rendering.gui.GUIScreen;
import net.projecty.client.utils.DisposeUtil;
import net.projecty.core.utils.LogUtil;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ProjectYClient implements GLEventListener {
	private ClientGame[] availableGames;
	private ClientGame currentGame;
	private GUIScreen screen;
	
	private float delta;
	private long time;
	
	public static void main(String[] args) {
		new ProjectYClient();
	}

	private ProjectYClient() {
		LogUtil.debug("ProjectYClient::construct() - START");

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
		
		LogUtil.debug("ProjectYClient::construct() - END");
	}
	
	@Override
	public void init(GLAutoDrawable drawable) {
		loadGames();
		screen = new GUIMainMenu();
		time = System.currentTimeMillis();
	}
	
	@Override
	public void display(GLAutoDrawable drawable) {
		screen.render(time, delta);
		long t = System.currentTimeMillis();
		delta = (float) (t - time) / 1000F;
		time = t;
	}
	
	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
		screen.onResize(width, height);
	}
	
	@Override
	public void dispose(GLAutoDrawable drawable) {
		LogUtil.debug("ProjectYClient::dispose() - STOP APP");
		DisposeUtil.disposeAll();
		System.exit(0);
	}
	
	private void loadGames() {
		File root = new File("./games");
		if (!root.exists()) root.mkdir();
		List<File> files = Arrays.stream(root.listFiles()).filter(f -> f.isDirectory()).toList();
		availableGames = new ClientGame[files.size()];
		for (int i = 0; i < files.size(); i++) {
			availableGames[i] = new ClientGame(files.get(i));
		}
	}
}