package za.ac.cput.factory;
import za.ac.cput.entity.Guest;
import java.time.LocalDate;

public class GuestFactory {

    // Factory method to create a Guest with full details including guestId
    public static Guest buildGuest(long guestId, String guestFirstName, String guestLastName,
                                   LocalDate guestDateOfBirth, String guestGender, String streetAddress, String suburb,
                                   String city, String postalCode, String country, String phoneNumber, String email) {
        if (guestFirstName == null || guestLastName == null || guestDateOfBirth == null || guestGender == null ||
                streetAddress == null || suburb == null || city == null || postalCode == null || country == null ||
                phoneNumber == null || email == null) {
            return null;
        }
        return new Guest.Builder()
                .setGuestId(guestId)
                .setGuestFirstName(guestFirstName)
                .setGuestLastName(guestLastName)
                .setGuestDateOfBirth(guestDateOfBirth)
                .setGuestGender(guestGender)
                .setStreetAddress(streetAddress)
                .setSuburb(suburb)
                .setCity(city)
                .setPostalCode(postalCode)
                .setCountry(country)
                .setPhoneNumber(phoneNumber)
                .setEmail(email)
                .build();
    }

    // Factory method to create a Guest without a guestId
    public static Guest buildGuestWithoutId(String guestFirstName, String guestLastName,
                                            LocalDate guestDateOfBirth, String guestGender, String streetAddress, String suburb,
                                            String city, String postalCode, String country, String phoneNumber, String email) {
        if (guestFirstName == null || guestLastName == null || guestDateOfBirth == null || guestGender == null ||
                streetAddress == null || suburb == null || city == null || postalCode == null || country == null ||
                phoneNumber == null || email == null) {
            return null;
        }
        return new Guest.Builder()
                .setGuestFirstName(guestFirstName)
                .setGuestLastName(guestLastName)
                .setGuestDateOfBirth(guestDateOfBirth)
                .setGuestGender(guestGender)
                .setStreetAddress(streetAddress)
                .setSuburb(suburb)
                .setCity(city)
                .setPostalCode(postalCode)
                .setCountry(country)
                .setPhoneNumber(phoneNumber)
                .setEmail(email)
                .build();
    }
}
