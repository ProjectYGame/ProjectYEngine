package net.projecty.core.math.vector;

import net.projecty.core.utils.MathUtil;

import java.util.Locale;
import java.util.Objects;

public class Vec2F {
	public float x;
	public float y;
	
	public Vec2F() {}
	
	public Vec2F(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public float lengthSqr() {
		return x * x + y * y;
	}
	
	public float length() {
		return MathUtil.sqrt(lengthSqr());
	}
	
	public float distanceSqr(Vec2F vector) {
		float dx = x - vector.x;
		float dy = y - vector.y;
		return dx * dx + dy * dy;
	}
	
	public float distance(Vec2F vector) {
		return MathUtil.sqrt(distanceSqr(vector));
	}
	
	public Vec2F add(float value) {
		return add(value, value);
	}
	
	public Vec2F add(float x, float y) {
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vec2F add(Vec2F vector) {
		return add(vector.x, vector.y);
	}
	
	public Vec2F set(float x, float y) {
		this.x = x;
		this.y = y;
		return this;
	}
	
	public Vec2F set(Vec2F vector) {
		return set(vector.x, vector.y);
	}
	
	public Vec2F subtract(float value) {
		return subtract(value, value);
	}
	
	public Vec2F subtract(float x, float y) {
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vec2F subtract(Vec2F vector) {
		return subtract(vector.x, vector.y);
	}
	
	public Vec2F divide(float value) {
		this.x /= value;
		this.y /= value;
		return this;
	}
	
	public Vec2F normalize() {
		float l = lengthSqr();
		return l > 0 ? this.divide(MathUtil.sqrt(l)) : this;
	}
	
	public Vec2F multiply(float value) {
		return multiply(value, value);
	}
	
	public Vec2F multiply(float x, float y) {
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Vec2F rotate(float angle) {
		float cos = (float) Math.cos(angle);
		float sin = (float) Math.sin(angle);
		float nx = x * cos - y * sin;
		float ny = y * cos + x * sin;
		return set(nx, ny);
	}
	
	public Vec2F invert() {
		this.x = -this.x;
		this.y = -this.y;
		return this;
	}
	
	public Vec2F lerp(Vec2F b, float delta) {
		this.x = MathUtil.lerp(this.x, b.x, delta);
		this.y = MathUtil.lerp(this.y, b.y, delta);
		return this;
	}
	
	@Override
	public String toString() {
		return String.format(Locale.ROOT, "[%f, %f]", x, y);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || !(obj instanceof Vec2F)) return false;
		Vec2F vec = (Vec2F) obj;
		return x == vec.x && y == vec.y;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}
	
	@Override
	public Vec2F clone() {
		return new Vec2F(x, y);
	}
}
