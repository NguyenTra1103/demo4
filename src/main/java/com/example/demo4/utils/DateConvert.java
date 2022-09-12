package com.example.demo4.utils;

import com.example.demo4.converter.DateTimeConverter;
import com.example.demo4.request.requests.CreateRequestLeaveType;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class DateConvert {


    public static Double calculateDayOff(CreateRequestLeaveType createRequestLeaveType){

        LocalDate dateStart = LocalDate.parse(createRequestLeaveType.getDayStart());
        LocalDate dateFinnish =LocalDate.parse(createRequestLeaveType.getDayEnd());

        Double result = Double.valueOf(ChronoUnit.DAYS.between(dateStart, dateFinnish));

        if (createRequestLeaveType.getShiftStart().equals(createRequestLeaveType.getShiftEnd())){

            return result + 0.5;

        }
        return result + 1;
    }
    public static int countTimeRemaining(String dayNewRequest, String dayOldRequest, int oldRemain) {
        String[] split = dayOldRequest.split(" - ");
        long millisNew = DateTimeConverter.fromStringToMillis(dayNewRequest);
        long millisOld = DateTimeConverter.fromStringToMillis(split[0]);
        Calendar calendarNew = Calendar.getInstance();
        calendarNew.setTimeInMillis(millisNew);
        Calendar calendarOld = Calendar.getInstance();
        calendarOld.setTimeInMillis(millisOld);
        int week1 = calendarNew.get(Calendar.WEEK_OF_YEAR);
        int week2 = calendarOld.get(Calendar.WEEK_OF_YEAR);
        if (week1 == week2) {
            return oldRemain-1;
        }
        return 1;
    }

}
