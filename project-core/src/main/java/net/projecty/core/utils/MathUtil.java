package net.projecty.core.utils;

import java.util.Random;

public class MathUtil {
	public static final float HALF_PI = (float) (Math.PI * 0.5F);
	public static final float PI = (float) Math.PI;
	public static final float PI2 = (float) (Math.PI * 2);
	private static final float[] SIN_TABLE;

	/**
	 * Clamps value to specified range.
	 * @param value - Value to clamp.
	 * @param min - Minimum output value.
	 * @param max - Maximum output value
	 * @return clamped value [min-max]
	 */
	public static int clamp(int value, int min, int max) {
		return value < min ? min : value > max ? max : value;
	}

	/**
	 * Clamps value to specified range.
	 * @param value - Value to clamp.
	 * @param min - Minimum output value.
	 * @param max - Maximum output value
	 * @return clamped value [min-max]
	 */
	public static float clamp(float value, float min, float max) {
		return value < min ? min : value > max ? max : value;
	}

	/**
	 * Clamps value to specified range.
	 * @param value - Value to clamp.
	 * @param min - Minimum output value.
	 * @param max - Maximum output value
	 * @return clamped value [min-max]
	 */
	public static double clamp(double value, double min, double max) {
		return value < min ? min : value > max ? max : value;
	}

	/**
	 * Linear interpolation between two values.
	 * @param a - Start value.
	 * @param b - End value.
	 * @param delta - Value change [0.0-1.0]
	 * @return linear interpolation [a-b]
	 */
	public static float lerp(float a, float b, float delta) {
		return a + delta * (b - a);
	}

	/**
	 * Linear interpolation between two values.
	 * @param a - Start value.
	 * @param b - End value.
	 * @param delta - Value change [0.0-1.0]
	 * @return linear interpolation [a-b]
	 */
	public static double lerp(double a, double b, double delta) {
		return a + delta * (b - a);
	}

	/**
	 * Convert X Y Z coordinates into index of array for 16x16x16 section.
	 * @param x X coordinate [0-15]
	 * @param y Y coordinate [0-15]
	 * @param z Z coordinate [0-15]
	 * @return array index [0-4095]
	 */
	public static int getIndex16(int x, int y, int z) {
		return x << 8 | y << 4 | z;
	}

	/**
	 * Calculates closets power of two value to the given number (greater or equal).
	 * Values below zero will be ignored.
	 * Example output: 756 -> 1024, 186 -> 256.
	 * @param value source number.
	 * @return closest power of two number (greater or equal).
	 */
	public static int getClosestPowerOfTwo(int value) {
		if (value <= 0) return 0;
		byte index = 0;
		byte count = 0;
		for (byte i = 0; i < 32; i++) {
			byte bit = (byte) (value & 1);
			if (bit == 1) {
				index = i;
				count++;
			}
			value >>>= 1;
		}
		return count == 1 ? 1 << index : 1 << (index + 1);
	}

	/**
	 * Wrap value in number circle (simple example - 12 hours clock format)
	 * @param value value to wrap
	 * @param side number circle size, value will be not in circle (exclusive)
	 * @return wrapped value
	 */
	public static int wrap(int value, int side) {
		int offset = value / side * side;
		if (offset > value) offset -= side;
		float delta = (float) (value - offset) / side;
		return (int) (delta * side);
	}

	/**
	 * Get random integer value in range.
	 * @param min min value, inclusive
	 * @param max max value, inclusive
	 * @param random {@link Random}
	 * @return int value in range
	 */
	public static int randomRange(int min, int max, Random random) {
		return min + random.nextInt(max - min + 1);
	}

	/**
	 * Get random float value in range.
	 * @param min min value, inclusive
	 * @param max max value, inclusive
	 * @param random {@link Random}
	 * @return float value in range
	 */
	public static float randomRange(float min, float max, Random random) {
		return min + random.nextFloat() * (max - min);
	}

	/**
	 * Shuffle array - keeps all values, but changes their order randomly.
	 * @param array an array to shuffle
	 * @param random {@link Random}
	 */
	public static <T> void shuffle(T[] array, Random random) {
		for (int i = 0; i < array.length; i++) {
			int i2 = random.nextInt(array.length);
			T val = array[i];
			array[i] = array[i2];
			array[i2] = val;
		}
	}

	public static float max(float a, float b) {
		return a > b ? a : b;
	}

	public static float max(float a, float b, float c) {
		return max(a, max(b, c));
	}

	public static int max(int a, int b) {
		return a > b ? a : b;
	}

	public static int max(int a, int b, int c) {
		return max(a, max(b, c));
	}

	public static float min(float a, float b) {
		return a < b ? a : b;
	}

	public static float min(float a, float b, float c) {
		return min(a, min(b, c));
	}

	public static int min(int a, int b) {
		return a < b ? a : b;
	}

	public static int min(int a, int b, int c) {
		return min(a, min(b, c));
	}

	/**
	 * Get square root of the number (cast of default math function)
	 */
	public static float sqrt(float x) {
		return (float) Math.sqrt(x);
	}

	public static int floor(float x) {
		return x < 0 ? (int) x - 1 : (int) x;
	}

	public static int floor(double x) {
		return x < 0 ? (int) x - 1 : (int) x;
	}
	
	public static int round(float x) {
		return floor(x + 0.5F);
	}
	
	public static int round(double x) {
		return floor(x + 0.5);
	}
	
	public static int ceil(float x) {
		return -floor(-x);
	}
	
	public static int ceil(double x) {
		return -floor(-x);
	}

	public static int abs(int x) {
		return x < 0 ? -x : x;
	}

	public static float abs(float x) {
		return x < 0 ? -x : x;
	}

	public static double abs(double x) {
		return x < 0 ? -x : x;
	}

	/**
	 * Get sine of the number (cast of default math function)
	 */
	public static float sin(float x) {
		return (float) Math.sin(x);
	}

	/**
	 * Get cosine of the number (cast of default math function)
	 */
	public static float cos(float x) {
		return (float) Math.cos(x);
	}

	/**
	 * Will return sine value using table interpolation, the error will be in range [E-6 - E-8]
	 * @param x value to calculate sine
	 * @return sine from internal table
	 */
	public static float fastSin(float x) {
		float fIndex = x / PI2 * 4096;
		int iIndex = floor(fIndex);
		int index1 = iIndex & 4095;
		int index2 = (iIndex + 1) & 4095;
		float delta = fIndex - iIndex;
		return lerp(SIN_TABLE[index1], SIN_TABLE[index2], delta);
	}

	/**
	 * Will return cosine value using table interpolation, the error will be in range [E-6 - E-8]
	 * @param x value to calculate cosine
	 * @return cosine from internal table
	 */
	public static float fastCos(float x) {
		return fastSin(x + HALF_PI);
	}

	static {
		SIN_TABLE = new float[4096];
		for (short i = 0; i < 4096; i++) {
			SIN_TABLE[i] = sin(i / 4096F * PI2);
		}
	}
}