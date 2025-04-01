package com.viet.service.Impl;

import com.stripe.exception.StripeException;
import com.viet.model.Order;
import com.viet.model.PaymentOrder;
import com.viet.model.User;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Set;

public interface PaymentService {
    PaymentOrder createOrder(User user, Set<Order> orders);
    PaymentOrder getPaymentOrderById(Long orderId) throws Exception;
    PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception;
    Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId);

    String createPaymentLink(HttpServletRequest req, User user, Long orderId, Long amount, String bankCode, String paymentLinkId) throws StripeException;
}
