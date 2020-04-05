package tech.minthura.carecovid.support;

import java.text.DecimalFormat;

import me.myatminsoe.mdetect.MDetect;
import tech.minthura.caresdk.Session;

public class NumberFormatter {

    public static NumberFormatter INSTANCE = new NumberFormatter();

    private String format(int number) {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###,##0");
        return decimalFormat.format(number);
    }

    public String formatToUnicode(int number) {
        String format = format(number);
        if(Session.getSession().getCurrentLanguage().equals("en")){
            return format;
        }
        return MDetect.INSTANCE.getText(NumberConverter.INSTANCE.toUnicodeNumber(format));
    }

    public String formatToUnicode(String number) {
        int num = 0;
        try {
            num = Integer.parseInt(number);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formatToUnicode(num);
    }

}
