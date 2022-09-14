package net.projecty.client.utils;

import net.projecty.core.utils.LogUtil;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public class TextUtil {
	private static final Font DEFAULT_FONT;
	
	public static Font getDefaultFont() {
		return DEFAULT_FONT;
	}
	
	static {
		Font font = null;
		try {
			font = Font.createFont(Font.TRUETYPE_FONT, TextUtil.class.getResourceAsStream("/Roboto-Medium.ttf"));
		}
		catch (FontFormatException | IOException e) {
			LogUtil.warn(e.getMessage());
			font = new Font("Monospaced", Font.PLAIN, 20);
		}
		DEFAULT_FONT = font.deriveFont(18F);
	}
}
