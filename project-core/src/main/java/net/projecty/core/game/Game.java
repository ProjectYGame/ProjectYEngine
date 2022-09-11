package net.projecty.core.game;

import java.io.File;

public abstract class Game {
	private final File folder;
	
	public Game(File folder) {
		this.folder = folder;
	}
}
