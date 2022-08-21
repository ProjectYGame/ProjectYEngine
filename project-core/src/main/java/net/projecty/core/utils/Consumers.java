package net.projecty.core.utils;

public interface Consumers {
	@FunctionalInterface
	public interface TriConsumer <A, B, C> {
		void accept(A a, B b, C c);
	}
	
	@FunctionalInterface
	public interface QuadConsumer <A, B, C, D> {
		void accept(A a, B b, C c, D d);
	}
	
	@FunctionalInterface
	public interface PentaConsumer <A, B, C, D, E> {
		void accept(A a, B b, C c, D d, E e);
	}
	
	@FunctionalInterface
	public interface HexConsumer <A, B, C, D, E, F> {
		void accept(A a, B b, C c, D d, E e, F f);
	}
}
