package pl.dashboard.nbp.service;

import java.time.LocalDate;

public class InputValidatorImpl implements InputValidator {

    public boolean validData(String[] args) {
        if (args == null || args.length == 0) {
            return false;
        }
        String date = args[0];
        String pattern = "\\d{4}-\\d{2}-\\d{2}";
        boolean isFormatCorrect = date.matches(pattern);
        if (!isFormatCorrect) {
            return false;
        }
        boolean isDateInFuture = isDateInFuture(date);
        if (isDateInFuture) {
            return false;
        }
        return true;
    }


    private boolean isDateInFuture(String date) {
        String[] splittedDate = date.split("-");
        Integer year = Integer.valueOf(splittedDate[0]);
        Integer month = Integer.valueOf(splittedDate[1]);
        Integer day = Integer.valueOf(splittedDate[2]);

        LocalDate now = LocalDate.now();
        LocalDate dateFromParam = LocalDate.of(year, month, day);
        boolean isFuture = now.isBefore(dateFromParam);
        return isFuture;
    }


}
