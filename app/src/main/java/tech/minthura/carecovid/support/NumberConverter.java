package tech.minthura.carecovid.support;

public class NumberConverter {

    private final String unicodeNumbers = "၀၁၂၃၄၅၆၇၈၉";

    static NumberConverter INSTANCE = new NumberConverter();

    String toUnicodeNumber(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if(Character.isDigit(c)){
                int index = c - '0';
                result.append(unicodeNumbers.charAt(index));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

}
