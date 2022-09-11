package net.projecty.client.utils;

import com.jogamp.opengl.GL2ES2;
import com.jogamp.opengl.GLContext;

public class GLUtil {
	public static GL2ES2 getGL2ES2() {
		return GLContext.getCurrentGL().getGL2ES2();
	}
}
