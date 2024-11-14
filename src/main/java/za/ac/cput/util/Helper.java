package za.ac.cput.util;

import java.util.UUID;

public class Helper {
    //feel free to edit with methods useful to your class, any changes made to the POM however,
    //must be stated.
    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }
    public static boolean isNullorEmpty(String s){
        return s == null || s.isEmpty();
    }

    public static boolean isNull(Boolean b) {
        return b == null;
    }

    public static UUID generateId(){
        return UUID.randomUUID();
    }
}
