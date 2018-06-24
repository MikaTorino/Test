import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LateArrivalTime {

	/** 乗車時間。a:自宅→A駅, b:A駅→B駅, c:B駅→会社 */
	public static LocalTime a = LocalTime.of(0, 00);
	public static LocalTime b = LocalTime.of(0, 00);
	public static LocalTime c = LocalTime.of(0, 00);

	/** 乗車時間範囲 */
	public static final LocalTime abcMin = LocalTime.of(0, 00);
	public static final LocalTime abcMax = LocalTime.of(0, 31);

	/** 発車時刻範囲（±1分） */
	public static final LocalTime h_iMin = LocalTime.of(5, 59);
	public static final LocalTime h_iMax = LocalTime.of(9, 00);

	/** 発車時刻 */
	public static ArrayList<LocalTime> hm_N = new ArrayList<>();

	/** 所要時間 */
	public static LocalTime timeRequired = LocalTime.of(0, 00);

	/** 出社時間（＋1分） */
	public static LocalTime arrivalTime = LocalTime.of(9, 00);

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			/** 乗車時間取得 */
			System.out.println("A駅までの時間、A駅からB駅の乗車時間、B駅から会社までの時間を半角スペース区切りで入力して下さい(範囲は1～30までです）");
			a = LocalTime.of(0, sc.nextInt());
			b = LocalTime.of(0, sc.nextInt());
			c = LocalTime.of(0, sc.nextInt());
			if (!(a.isAfter(abcMin) && b.isBefore(abcMax) && b.isAfter(abcMin) && c.isBefore(abcMax)
					&& c.isAfter(abcMin) && c.isBefore(abcMax))) {
				throw new MyException_OutOfRange("数値は1～30の範囲で入力して下さい");
			}
			System.out.println("A駅までの時間：" + a);
			System.out.println("A駅からB駅の乗車時間：" + b);
			System.out.println("B駅から会社までの時間：" + c);

			/** A駅から出る電車の本数を取得 */
			System.out.println("A駅から出る電車の本数を整数で入力して下さい");
			int N = sc.nextInt();
			if (!(1 <= N && N <= 180)) {
				throw new MyException_OutOfRange("数値は1～180の範囲で入力して下さい");
			}
			System.out.println("A駅から出る電車の本数：" + N);

			/** 電車の発車時刻を取得 */
			for (int i = 0; i < N; i++) {
				System.out.println(i + 1 + "本目の電車の発車時刻をHH mmで入力して下さい");
				LocalTime hmCheck = LocalTime.of(sc.nextInt(), sc.nextInt());
				if (hmCheck.isAfter(h_iMin) && hmCheck.isBefore(h_iMax)) {
					hm_N.add(hmCheck);
				} else {
					throw new MyException_OutOfRange("時刻は6:00から8:59の範囲で入力して下さい");
				}
				System.out.println(i + 1 + "本目の電車の発車時刻：" + hmCheck);
			}
		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (MyException_OutOfRange e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}

		/** 電車の発車時刻を降順にソート */
		for (int i = 0; i < hm_N.size(); i++) {
			Arrays.sort(hm_N.get(i), Collections.reverseOrder());
		}

		/** 出社時間に間に合うA駅から出る一番遅い電車を格納、最遅出社時刻を表示 */
		LocalTime lateTrain = LocalTime.of(0, 0);
		for (LocalTime t : hm_N) {
			if (t.isBefore(arrivalTime.minusMinutes(b.getMinute() + c.getMinute()))) {
				lateTrain = t;
				System.out.println("最遅出社時刻：" + lateTrain.minusMinutes(a.getMinute()));
				break;
			}
		}
	}
}
