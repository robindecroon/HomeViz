package robindecroon.stackoverflow;

/**
 * Generate N random numbers when their SUM is known
 */
import java.util.Random;

/**
 * Klasse gevonden op
 * <link>http://letmehelpyougeeks.blogspot.be/2009/02/generate
 * -n-random-numbers-when-their.html</link>
 * 
 * @author Deepak Azad
 */

public class RandomNumberGenerator {

	public static int[] genNumbers(int n, int sum) {
		int[] nums = new int[n];
		int upperbound = Long.valueOf(Math.round(sum * 1.0 / n)).intValue();
		int offset = Long.valueOf(Math.round(0.5 * upperbound)).intValue();

		int cursum = 0;
		Random random = new Random(new Random().nextInt());
		for (int i = 0; i < n; i++) {
			int rand = random.nextInt(upperbound) + offset;
			if (cursum + rand > sum || i == n - 1) {
				rand = sum - cursum;
			}
			cursum += rand;
			nums[i] = rand;
			if (cursum == sum) {
				break;
			}
		}
		return nums;
	}
}