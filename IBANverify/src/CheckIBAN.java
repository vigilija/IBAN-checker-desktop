import java.math.BigInteger;

public class CheckIBAN {
	public static final int IBANNUMBER_MIN_SIZE = 15;
    public static final int IBANNUMBER_MAX_SIZE = 34;
    public static final BigInteger IBANNUMBER_MAGIC_NUMBER = new BigInteger("97");

    public static boolean ibanTest(String accountNumber) {
        String newAccountNumber = accountNumber.trim();
        if (newAccountNumber.length() < IBANNUMBER_MIN_SIZE || newAccountNumber.length() > IBANNUMBER_MAX_SIZE) {
            return false;
        }
        newAccountNumber = newAccountNumber.substring(4) + newAccountNumber.substring(0, 4);
        StringBuilder numericAccountNumber = new StringBuilder();
        try {
        for (int i = 0;i < newAccountNumber.length();i++) {
            numericAccountNumber.append(Character.getNumericValue(newAccountNumber.charAt(i)));
        }
        BigInteger ibanNumber = new BigInteger(numericAccountNumber.toString());
        return ibanNumber.mod(IBANNUMBER_MAGIC_NUMBER).intValue() == 1;
        }
        catch (NumberFormatException ex) {
        	return false;
        }

    }

}
