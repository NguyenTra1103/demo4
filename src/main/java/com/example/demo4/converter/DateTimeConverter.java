package com.example.demo4.converter;

import com.example.demo4.exception.CustomException;
import com.example.demo4.response.ObjectResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DateTimeConverter {
    public static String fromDateToString(Date time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        return format.format(time);
    }
    public static Date fromStringToDate(String time){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        Date parse;
        try {
            parse = format.parse(time);
        } catch (ParseException e) {
            throw new CustomException(ObjectResponse.STATUS_CODE_BAD_REQUEST,ObjectResponse.MESSAGE_FAIL_TO_PARSE_FROM_STRING_TO_LONG);
        }
        return parse;
    }
    public static String convertLongToDate(Long agr) {
        Date date = new Date(agr);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(date);
    }
    public static long fromStringToMillis(String strDate) {

        return fromStringToDate(strDate).getTime();
    }
    public static int getCurrentYear() {
        return LocalDateTime.now().getYear();
    }
}
