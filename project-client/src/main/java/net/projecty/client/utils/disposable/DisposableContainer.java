package net.projecty.client.utils.disposable;

public class DisposableContainer implements Disposable {
	private final Runnable dispose;
	private boolean disposed;
	
	public DisposableContainer(Runnable dispose) {
		this.dispose = dispose;
	}
	
	@Override
	public void dispose() {
		if (!disposed) {
			disposed = true;
			dispose.run();
		}
	}
}
