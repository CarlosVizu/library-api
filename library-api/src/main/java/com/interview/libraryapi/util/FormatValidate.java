package com.interview.libraryapi.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class FormatValidate {
    public static boolean validarEmail(String email) {
        String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    public static boolean validarDataAnteriorHoje(Date data) {
        Calendar calendario = Calendar.getInstance();
        Date dataAtual = calendario.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        try {
            Date dataFormatada = dateFormat.parse(dateFormat.format(data));
            Date dataAtualFormatada = dateFormat.parse(dateFormat.format(dataAtual));

            return dataFormatada.before(dataAtualFormatada);

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean isDatasIguais(Date data1, Date data2) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate1 = sdf.format(data1);
        String formattedDate2 = sdf.format(data2);

        return formattedDate1.equals(formattedDate2);
    }

}
