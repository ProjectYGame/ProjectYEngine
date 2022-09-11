package net.projecty.client.game;

import com.jogamp.opengl.util.texture.Texture;
import net.projecty.client.utils.GLUtil;
import net.projecty.core.game.Game;

import java.io.File;

public class ClientGame extends Game {
	private final Texture icon;
	
	public ClientGame(File folder) {
		super(folder);
		
		Texture texture = null;
		File file = new File(folder, "icon.png");
		if (file.exists()) {
			texture = GLUtil.loadTexture(file, false);
		}
		icon = texture == null ? GLUtil.getEmpty() : texture;
	}
	
	public Texture getIcon() {
		return icon;
	}
}
