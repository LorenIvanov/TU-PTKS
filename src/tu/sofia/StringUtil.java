package tu.sofia;

import java.security.MessageDigest;

public class StringUtil {

    // Applies Sha256 to a string and returns the result.
    public static String applySha256(String input) {
        MessageDigest digest = null;
        byte[] hash = null;
        StringBuilder hexString = null;

        try {
            digest = MessageDigest.getInstance("SHA-256");
            // Applies sha256 to our input,
            hash = digest.digest(input.getBytes("UTF-8"));

            // This will contain hash as hexadecimal
            hexString = new StringBuilder();

            for (int _byte : hash) {
                String hex = Integer.toHexString(0xff & _byte);

                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return hexString.toString();
    }
}
