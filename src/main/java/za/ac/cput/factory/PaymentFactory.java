package za.ac.cput.factory;

import za.ac.cput.entity.Booking;
import za.ac.cput.entity.Payment;
import za.ac.cput.entity.enums.PaymentMethod;
import za.ac.cput.entity.enums.PaymentStatus;

import java.time.LocalDateTime;

public class PaymentFactory {

    // Factory method to create a Payment with all details
    public static Payment buildPayment(long paymentId, String transactionId, LocalDateTime paymentDate,
                                       Booking booking, double totalPrice, PaymentStatus paymentStatus,
                                       PaymentMethod paymentMethod) {
        if (paymentId < 0 || transactionId == null || paymentDate == null || booking == null ||
                totalPrice <= 0 || paymentStatus == null || paymentMethod == null) {
            return null;
        }
        return new Payment.Builder()
                .setPaymentId(paymentId)
                .setTransactionId(transactionId)
                .setPaymentDate(paymentDate)
                .setBooking(booking)
                .setTotalPrice(totalPrice)
                .setPaymentStatus(paymentStatus)
                .setPaymentMethod(paymentMethod)
                .build();
    }

    // Factory method to create a Payment without an ID
    public static Payment buildPaymentWithoutId(String transactionId, LocalDateTime paymentDate,
                                                Booking booking, double totalPrice, PaymentStatus paymentStatus,
                                                PaymentMethod paymentMethod) {
        if (transactionId == null || paymentDate == null || booking == null ||
                totalPrice <= 0 || paymentStatus == null || paymentMethod == null) {
            return null;
        }
        return new Payment.Builder()
                .setTransactionId(transactionId)
                .setPaymentDate(paymentDate)
                .setBooking(booking)
                .setTotalPrice(totalPrice)
                .setPaymentStatus(paymentStatus)
                .setPaymentMethod(paymentMethod)
                .build();
    }
}

