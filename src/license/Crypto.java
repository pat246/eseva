package license;

public class Crypto {
	static String key = "Bar12345Bar12345";

	public Crypto() {
	}

	public String encrypt(String text) throws Exception {
		char[] decimals = text.toCharArray();
		StringBuffer sb = new StringBuffer();
		for (char c : decimals) {
			sb.append(decimalToBinary(c - '0'));
		}
		return sb.toString();
	}

	public String decrypt(String textEn) throws Exception {
		int s = 0;
		int e = 4;
		String num = "";
		while (e <= textEn.length()) {
			num = num + BtoD(textEn.substring(s, e));
			e += 4;
			s += 4;
		}
		return num;
	}

	public String decimalToBinary(int value) {
		int count = 4;
		int[] intArray = new int[4];
		int tmp = value;
		while (tmp != 0) {
			if (tmp % 2 == 0) {
				intArray[(count - 1)] = 0;
			} else {
				intArray[(count - 1)] = 1;
			}
			tmp /= 2;
			count--;
		}
		String s = intArray[0] + intArray[1] + intArray[2] + intArray[3] + "";
		return s;
	}

	public String BtoD(String str) {
		char[] bins = str.toCharArray();
		int ret = 0;
		ret += (bins[0] - '0') * 8;
		ret += (bins[1] - '0') * 4;
		ret += (bins[2] - '0') * 2;
		ret += (bins[3] - '0') * 1;
		return ret + "";
	}
}
