package net.projecty.client.utils.disposable;

import com.jogamp.opengl.GL2ES2;
import net.projecty.client.utils.GLUtil;

import java.util.function.Consumer;

public class GLDisposableContainer implements Disposable {
	private final Consumer<GL2ES2> dispose;
	private boolean disposed;
	
	public GLDisposableContainer(Consumer<GL2ES2> dispose) {
		this.dispose = dispose;
	}
	
	@Override
	public void dispose() {
		if (!disposed) {
			disposed = true;
			dispose.accept(GLUtil.getGL2ES2());
		}
	}
}
