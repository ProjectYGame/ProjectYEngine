package net.projecty.client.rendering.gui;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import net.projecty.client.game.ClientGame;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class GUIMainMenu extends GUIScreen {
	private ClientGame[] availableGames;
	private ClientGame currentGame;
	
	public GUIMainMenu() {
		super(null);
		loadGames();
	}
	
	@Override
	void renderBackground(GLAutoDrawable drawable, long time, float delta) {
		GL gl = drawable.getGL();
		GL2 gl2 = gl.getGL2();
		
		gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
		
		float halh = availableGames.length * 35F;
		for (int i = 0; i < availableGames.length; i++) {
			float offset = i * 70F - halh;
			gl2.glEnable(GL3.GL_TEXTURE_2D);
			availableGames[i].getIcon().bind(gl);
			blit(drawable, offset + CENTER.x, CENTER.y - 30, 60, 60);
		}
	}
	
	private void loadGames() {
		File root = new File("./games");
		if (!root.exists()) root.mkdir();
		List<File> files = Arrays.stream(root.listFiles()).filter(f -> f.isDirectory()).toList();
		availableGames = new ClientGame[files.size()];
		for (int i = 0; i < files.size(); i++) {
			availableGames[i] = new ClientGame(files.get(i));
		}
		if (availableGames.length > 0) {
			currentGame = availableGames[0];
		}
	}
}
