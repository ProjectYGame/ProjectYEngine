package net.projecty.core.math.vector;

import net.projecty.core.utils.MathUtil;

import java.util.Locale;
import java.util.Objects;

public class Vec2I {
	public int x;
	public int y;
	
	public Vec2I() {}
	
	public Vec2I(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int lengthSqr() {
		return x * x + y * y;
	}
	
	public float length() {
		return MathUtil.sqrt(lengthSqr());
	}
	
	public float distanceSqr(Vec2I vector) {
		float dx = x - vector.x;
		float dy = y - vector.y;
		return dx * dx + dy * dy;
	}
	
	public float distance(Vec2I vector) {
		return MathUtil.sqrt(distanceSqr(vector));
	}
	
	public Vec2I add(int value) {
		return add(value, value);
	}
	
	public Vec2I add(int x, int y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vec2I add(Vec2I vector) {
		return add(vector.x, vector.y);
	}
	
	public Vec2I set(int x, int y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vec2I set(Vec2I vector) {
		return set(vector.x, vector.y);
	}
	
	public Vec2I subtract(int value) {
		return subtract(value, value);
	}
	
	public Vec2I subtract(int x, int y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vec2I subtract(Vec2I vector) {
		return subtract(vector.x, vector.y);
	}
	
	public Vec2I divide(int value) {
		this.x /= value;
		this.y /= value;
		return this;
	}
	
	public Vec2I multiply(int value) {
		return multiply(value, value);
	}
	
	public Vec2I multiply(int x, int y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Vec2I rotateCW() {
		int nx = -y;
		int ny = x;
		return set(nx, ny);
	}
	
	public Vec2I rotateCCW() {
		int nx = y;
		int ny = -x;
		return set(nx, ny);
	}
	
	public Vec2I invert() {
		this.x = -this.x;
		this.y = -this.y;
		return this;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "[%d, %d]", x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || !(obj instanceof Vec2I)) return false;
		Vec2I vec = (Vec2I) obj;
		return x == vec.x && y == vec.y;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	@Override
	public Vec2I clone() {
		return new Vec2I(x, y);
	}
}
