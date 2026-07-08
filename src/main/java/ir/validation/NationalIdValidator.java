package ir.validation;

import org.springframework.stereotype.Component;

@Component
public class NationalIdValidator {

    public boolean isValid(String nationalId) {
        if (nationalId == null) return false;
        if (!nationalId.matches("\\d{10}")) return false;

        // جلوگیری از کدهای 10 رقم یکسان (مثل 0000000000)
        if (nationalId.chars().distinct().count() == 1) {
            return false;
        }

        int check = Character.getNumericValue(nationalId.charAt(9));
        int sum = 0;

        for (int i = 0; i < 9; i++) {
            int digit = Character.getNumericValue(nationalId.charAt(i));
            sum += digit * (10 - i);
        }

        int remainder = sum % 11;

        return (remainder < 2 && check == remainder) ||
                (remainder >= 2 && check == (11 - remainder));
    }
}

