package mate.academy.model;

import java.util.Arrays;

public class Kata {

	public static long[] wheatFromChaff(long[] values) {
		int countm = 0;
		int countp = 0;
		for (int i = 0; i < values.length; i++) {
			if (values[i] < 0) {
				countm ++;
			} else {
				countp ++;
			}
		}
		long[] minus = new long[countm];
		long[] plus = new long[countp];

		for (long value : values) {
			for (int y = 0; y < minus.length; y++) {
				if (value < 0) {
					minus[y] = value;
				}
			}
		}
		for (long value : values) {
			for (int y = 0; y < values.length; y++) {
				if (value >= 0) {
					plus[y] = value;
				}
			}
		}
		long[] res = new long[values.length];
		int count = 0;
		for(int i = 0; i<res.length; i++) {
			res[i] = minus[i];
			count++;
		}
		for (long l : plus) {
			res[count++] = l;
		}

		return res;
	}
}