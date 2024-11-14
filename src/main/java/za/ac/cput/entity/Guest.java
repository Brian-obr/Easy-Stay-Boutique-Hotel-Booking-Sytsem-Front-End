package za.ac.cput.entity;

import java.time.LocalDate;
import java.util.Objects;

public class Guest {
    private long guestId;
    private String guestFirstName;
    private String guestLastName;
    private LocalDate guestDateOfBirth;
    private String guestGender;
    private String phoneNumber;
    private String streetAddress;
    private String suburb;  // Fixed typo
    private String city;
    private String postalCode;
    private String country;
    private String email;
    protected Guest() {}

    private Guest(Builder builder) {
        this.guestId = builder.guestId;
        this.guestFirstName = builder.guestFirstName;
        this.guestLastName = builder.guestLastName;
        this.guestDateOfBirth = builder.guestDateOfBirth;
        this.guestGender = builder.guestGender;
        this.phoneNumber = builder.phoneNumber;
        this.streetAddress = builder.streetAddress;
        this.suburb = builder.suburb;
        this.city = builder.city;
        this.postalCode = builder.postalCode;
        this.country = builder.country;
        this.email = builder.email;
    }

    public Long getGuestId() {
        return guestId;
    }

    public String getGuestFirstName() {
        return guestFirstName;
    }

    public String getGuestLastName() {
        return guestLastName;
    }

    public LocalDate getGuestDateOfBirth() {
        return guestDateOfBirth;
    }

    public String getGuestGender() {
        return guestGender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public String getSuburb() {
        return suburb;
    }

    public String getCity() {
        return city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getCountry() {
        return country;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Guest guest = (Guest) o;
        return guestId == guest.guestId && Objects.equals(guestFirstName, guest.guestFirstName) && Objects.equals(guestLastName, guest.guestLastName) && Objects.equals(guestDateOfBirth, guest.guestDateOfBirth) && Objects.equals(guestGender, guest.guestGender) && Objects.equals(phoneNumber, guest.phoneNumber) && Objects.equals(streetAddress, guest.streetAddress) && Objects.equals(suburb, guest.suburb) && Objects.equals(city, guest.city) && Objects.equals(postalCode, guest.postalCode) && Objects.equals(country, guest.country) && Objects.equals(email, guest.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guestId, guestFirstName, guestLastName, guestDateOfBirth, guestGender, phoneNumber, streetAddress, suburb, city, postalCode, country, email);
    }

    @Override
    public String toString() {
        return "Guest{" +
                "guestId=" + guestId +
                ", guestFirstName='" + guestFirstName + '\'' +
                ", guestLastName='" + guestLastName + '\'' +
                ", guestDateOfBirth=" + guestDateOfBirth +
                ", guestGender='" + guestGender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", streetAddress='" + streetAddress + '\'' +
                ", suburb='" + suburb + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static class Builder {
        private long guestId;
        private String guestFirstName;
        private String guestLastName;
        private LocalDate guestDateOfBirth;
        private String guestGender;
        private String phoneNumber;
        private String streetAddress;
        private String suburb;  // Fixed typo
        private String city;
        private String postalCode;
        private String country;
        private String email;

        public Builder setGuestId(Long guestId) {
            this.guestId = guestId;
            return this;
        }

        public Builder setGuestFirstName(String guestFirstName) {
            this.guestFirstName = guestFirstName;
            return this;
        }
        public Builder setGuestLastName(String guestLastName) {
            this.guestLastName = guestLastName;
            return this;
        }
        public Builder setGuestDateOfBirth(LocalDate guestDateOfBirth) {
            this.guestDateOfBirth = guestDateOfBirth;
            return this;
        }
        public Builder setGuestGender(String guestGender) {
            this.guestGender = guestGender;
            return this;
        }
        public Builder setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }
        public Builder setStreetAddress(String streetAddress) {
            this.streetAddress = streetAddress;
            return this;
        }
        public Builder setSuburb(String suburb) {
            this.suburb = suburb;
            return this;
        }
        public Builder setCity(String city) {
            this.city = city;
            return this;
        }

        public Builder setPostalCode(String postalCode) {
            this.postalCode = postalCode;
            return this;
        }

        public Builder setCountry(String country) {
            this.country = country;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder copy(Guest guest) {
            this.guestId = guest.guestId;
            this.guestFirstName = guest.guestFirstName;
            this.guestLastName = guest.guestLastName;
            this.guestDateOfBirth = guest.guestDateOfBirth;
            this.guestGender = guest.guestGender;
            this.phoneNumber = guest.phoneNumber;
            this.streetAddress = guest.streetAddress;
            this.suburb = guest.suburb;
            this.city = guest.city;
            this.postalCode = guest.postalCode;
            this.country = guest.country;
            this.email = guest.email;
            return this;
        }
        public Guest build() {
            return new Guest(this);
        }
    }
}