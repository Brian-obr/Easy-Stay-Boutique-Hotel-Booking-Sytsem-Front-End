package za.ac.cput.entity;
import za.ac.cput.entity.enums.PaymentMethod;
import za.ac.cput.entity.enums.PaymentStatus;

import java.time.LocalDateTime;
import java.util.Objects;

public class Payment {
    private long paymentId;
    private String transactionId;
    private LocalDateTime paymentDate;
    private Booking booking;
    private double totalPrice;
    private PaymentStatus paymentStatus;
    private PaymentMethod paymentMethod;

    protected Payment() {}

    private Payment(Builder builder) {
        this.paymentId = builder.paymentId;
        this.transactionId = builder.transactionId;
        this.paymentDate = builder.paymentDate;
        this.booking = builder.booking;
        this.totalPrice = builder.totalPrice;
        this.paymentStatus = builder.paymentStatus;
        this.paymentMethod = builder.paymentMethod;
    }

    public long getPaymentId() {
        return paymentId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return paymentId == payment.paymentId && Double.compare(totalPrice, payment.totalPrice) == 0 && Objects.equals(transactionId, payment.transactionId) && Objects.equals(paymentDate, payment.paymentDate) && Objects.equals(booking, payment.booking) && paymentStatus == payment.paymentStatus && paymentMethod == payment.paymentMethod;
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentId, transactionId, paymentDate, booking, totalPrice, paymentStatus, paymentMethod);
    }

    @Override
    public String toString() {
        return "Payment{" +
                "paymentId=" + paymentId +
                ", transactionId='" + transactionId + '\'' +
                ", paymentDate=" + paymentDate +
                ", booking=" + booking +
                ", totalPrice=" + totalPrice +
                ", paymentStatus=" + paymentStatus +
                ", paymentMethod=" + paymentMethod +
                '}';
    }

    public static class Builder{
        private long paymentId;
        private String transactionId;
        private LocalDateTime paymentDate;
        private Booking booking;
        private double totalPrice;
        private PaymentStatus paymentStatus;
        private PaymentMethod paymentMethod;

        public Builder setPaymentId(long paymentId) {
            this.paymentId = paymentId;
            return this;
        }
        public Builder setTransactionId(String transactionId) {
            this.transactionId = transactionId;
            return this;
        }
        public Builder setPaymentDate(LocalDateTime paymentDate) {
            this.paymentDate = paymentDate;
            return this;
        }
        public Builder setBooking(Booking booking) {
            this.booking = booking;
            return this;
        }

        public Builder setTotalPrice(double totalPrice) {
            this.totalPrice = totalPrice;
            return this;
        }

        public Builder setPaymentStatus(PaymentStatus paymentStatus) {
            this.paymentStatus = paymentStatus;
            return this;
        }

        public Builder setPaymentMethod(PaymentMethod paymentMethod) {
            this.paymentMethod = paymentMethod;
            return this;
        }

        public Builder copy(Payment payment) {
            this.paymentId = payment.paymentId;
            this.transactionId = payment.transactionId;
            this.paymentDate = payment.paymentDate;
            this.booking = payment.booking;
            this.totalPrice = payment.totalPrice;
            this.paymentStatus = payment.paymentStatus;
            this.paymentMethod = payment.paymentMethod;
            return this;
        }

        public Payment build() {
            return new Payment(this);
        }
    }
}
