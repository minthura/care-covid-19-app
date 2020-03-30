package tech.minthura.carecovid.support;

import java.text.DecimalFormat;
import java.util.Locale;

import me.myatminsoe.mdetect.MDetect;
import tech.minthura.caresdk.Session;

public class NumberFormatter {

    public static NumberFormatter INSTANCE = new NumberFormatter();

    public String format(int number) {
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

}
