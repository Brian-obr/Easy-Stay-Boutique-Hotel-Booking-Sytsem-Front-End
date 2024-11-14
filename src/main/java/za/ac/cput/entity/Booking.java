package za.ac.cput.entity;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Booking {
    private long bookingId;
    private LocalDateTime bookingDate;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private double totalAmount;
    private Guest guest;
    private Room room;

    protected Booking() {}

    private Booking(Builder builder){
        this.bookingId = builder.bookingId;
        this.bookingDate = builder.bookingDate;
        this.checkInDate = builder.checkInDate;
        this.checkOutDate = builder.checkOutDate;
        this.totalAmount = builder.totalAmount;
        this.guest = builder.guest;
        this.room = builder.room;
    }

    public long getBookingId() {
        return bookingId;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public Guest getGuest() {
        return guest;
    }

    public Room getRoom() {
        return room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return bookingId == booking.bookingId && Double.compare(totalAmount, booking.totalAmount) == 0 && Objects.equals(bookingDate, booking.bookingDate) && Objects.equals(checkInDate, booking.checkInDate) && Objects.equals(checkOutDate, booking.checkOutDate) && Objects.equals(guest, booking.guest) && Objects.equals(room, booking.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookingId, bookingDate, checkInDate, checkOutDate, totalAmount, guest, room);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", bookingDate=" + bookingDate +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", totalAmount=" + totalAmount +
                ", guest=" + guest +
                ", room=" + room +
                '}';
    }

    public static class Builder {
        private long bookingId;
        private LocalDateTime bookingDate;
        private LocalDate checkInDate;
        private LocalDate checkOutDate;
        private double totalAmount;
        private Guest guest;
        private Room room;

        public Builder setBookingId(long bookingId) {
            this.bookingId = bookingId;
            return this;
        }

        public Builder setBookingDate(LocalDateTime bookingDate) {
            this.bookingDate = bookingDate;
            return this;
        }

        public Builder setCheckInDate(LocalDate checkInDate) {
            this.checkInDate = checkInDate;
            return this;
        }
        public Builder setCheckOutDate(LocalDate checkOutDate) {
            this.checkOutDate = checkOutDate;
            return this;
        }

        public Builder setTotalAmount(double totalAmount) {
            this.totalAmount = totalAmount;
            return this;
        }
        public Builder setGuest(Guest guest) {
            this.guest = guest;
            return this;
        }

        public Builder setRoom(Room room) {
            this.room = room;
            return this;
        }

        public Builder copy(Booking booking){
            this.bookingId = booking.bookingId;
            this.bookingDate = booking.bookingDate;
            this.checkInDate = booking.checkInDate;
            this.checkOutDate = booking.checkOutDate;
            this.totalAmount = booking.totalAmount;
            this.guest = booking.guest;
            this.room = booking.room;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}
