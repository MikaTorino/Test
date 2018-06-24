import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

public class LateArrivalTime {

	/** ��Ԏ��ԁBa:���A�w, b:A�w��B�w, c:B�w����� */
	public static LocalTime a = LocalTime.of(0, 00);
	public static LocalTime b = LocalTime.of(0, 00);
	public static LocalTime c = LocalTime.of(0, 00);

	/** ��Ԏ��Ԕ͈� */
	public static final LocalTime abcMin = LocalTime.of(0, 00);
	public static final LocalTime abcMax = LocalTime.of(0, 31);

	/** ���Ԏ����͈́i�}1���j */
	public static final LocalTime h_iMin = LocalTime.of(5, 59);
	public static final LocalTime h_iMax = LocalTime.of(9, 00);

	/** ���Ԏ��� */
	public static ArrayList<LocalTime> hm_N = new ArrayList<>();

	/** ���v���� */
	public static LocalTime timeRequired = LocalTime.of(0, 00);

	/** �o�Ў��ԁi�{1���j */
	public static LocalTime arrivalTime = LocalTime.of(9, 00);

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		try {
			/** ��Ԏ��Ԏ擾 */
			System.out.println("A�w�܂ł̎��ԁAA�w����B�w�̏�Ԏ��ԁAB�w�����Ђ܂ł̎��Ԃ𔼊p�X�y�[�X��؂�œ��͂��ĉ�����(�͈͂�1�`30�܂łł��j");
			a = LocalTime.of(0, sc.nextInt());
			b = LocalTime.of(0, sc.nextInt());
			c = LocalTime.of(0, sc.nextInt());
			if (!(a.isAfter(abcMin) && b.isBefore(abcMax) && b.isAfter(abcMin) && c.isBefore(abcMax)
					&& c.isAfter(abcMin) && c.isBefore(abcMax))) {
				throw new MyException_OutOfRange("���l��1�`30�͈̔͂œ��͂��ĉ�����");
			}
			System.out.println("A�w�܂ł̎��ԁF" + a);
			System.out.println("A�w����B�w�̏�Ԏ��ԁF" + b);
			System.out.println("B�w�����Ђ܂ł̎��ԁF" + c);

			/** A�w����o��d�Ԃ̖{�����擾 */
			System.out.println("A�w����o��d�Ԃ̖{���𐮐��œ��͂��ĉ�����");
			int N = sc.nextInt();
			if (!(1 <= N && N <= 180)) {
				throw new MyException_OutOfRange("���l��1�`180�͈̔͂œ��͂��ĉ�����");
			}
			System.out.println("A�w����o��d�Ԃ̖{���F" + N);

			/** �d�Ԃ̔��Ԏ������擾 */
			for (int i = 0; i < N; i++) {
				System.out.println(i + 1 + "�{�ڂ̓d�Ԃ̔��Ԏ�����HH mm�œ��͂��ĉ�����");
				LocalTime hmCheck = LocalTime.of(sc.nextInt(), sc.nextInt());
				if (hmCheck.isAfter(h_iMin) && hmCheck.isBefore(h_iMax)) {
					hm_N.add(hmCheck);
				} else {
					throw new MyException_OutOfRange("������6:00����8:59�͈̔͂œ��͂��ĉ�����");
				}
				System.out.println(i + 1 + "�{�ڂ̓d�Ԃ̔��Ԏ����F" + hmCheck);
			}
		} catch (InputMismatchException e) {
			e.printStackTrace();
		} catch (MyException_OutOfRange e) {
			e.printStackTrace();
		} finally {
			sc.close();
		}

		/** �d�Ԃ̔��Ԏ������~���Ƀ\�[�g */
		for (int i = 0; i < hm_N.size(); i++) {
			Arrays.sort(hm_N.get(i), Collections.reverseOrder());
		}

		/** �o�Ў��ԂɊԂɍ���A�w����o���Ԓx���d�Ԃ��i�[�A�Œx�o�Ў�����\�� */
		LocalTime lateTrain = LocalTime.of(0, 0);
		for (LocalTime t : hm_N) {
			if (t.isBefore(arrivalTime.minusMinutes(b.getMinute() + c.getMinute()))) {
				lateTrain = t;
				System.out.println("�Œx�o�Ў����F" + lateTrain.minusMinutes(a.getMinute()));
				break;
			}
		}
	}
}
