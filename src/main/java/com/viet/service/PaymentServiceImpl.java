package com.viet.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.viet.config.VNPAYConfig;
import com.viet.domain.PaymentOrderStatus;
import com.viet.model.Order;
import com.viet.model.PaymentOrder;
import com.viet.model.User;
import com.viet.repository.OrderRepository;
import com.viet.repository.PaymentOrderRepository;
import com.viet.response.PaymentResponse;
import com.viet.service.Impl.PaymentService;
import com.viet.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentServiceImpl implements PaymentService {

    PaymentOrderRepository paymentOrderRepository;
    OrderRepository orderRepository;
    VNPAYConfig vnPayConfig;

    @Override
    public PaymentOrder createOrder(User user, Set<Order> orders) {
        Long amount = orders.stream().mapToLong(Order::getTotalSellingPrice).sum();

        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setAmount(amount);
        paymentOrder.setUser(user);
        paymentOrder.setOrders(orders);

        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long orderId) throws Exception {
        return paymentOrderRepository.findById(orderId).orElseThrow(() -> new Exception("payment order not found"));
    }

    @Override
    public PaymentOrder getPaymentOrderByPaymentId(String paymentId) throws Exception {
        PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentLinkId(paymentId);
        if (paymentOrder == null) {
            throw new Exception("payment order not found with payment link id - " + paymentId);
        }
        return paymentOrder;
    }

    @Override
    public Boolean proceedPayment(PaymentOrder paymentOrder, String paymentId, String paymentLinkId) {

        if(paymentOrder.getStatus().equals(PaymentOrderStatus.PENDING)){

        }
        return null;
    }

    @Override
    public String createPaymentLink(HttpServletRequest req, User user, Long orderId ,Long amount, String bankCode, String paymentLinkId) throws StripeException {
//        Stripe.apiKey = stripeSecretKey;
//        SessionCreateParams sessionCreateParams = SessionCreateParams.builder()
//                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
//                .setMode(SessionCreateParams.Mode.PAYMENT)
//                .setSuccessUrl("http://localhost:3000/payment-success/" + orderId)
//                .setCancelUrl("http://localhost:3000/payment-cancel")
//                .addLineItem(SessionCreateParams.LineItem.builder().setQuantity(1L)
//                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
//                                .setCurrency("usd")
//                                .setUnitAmount(amount*100)
//                                .setProductData(SessionCreateParams.LineItem.PriceData
//                                        .ProductData.builder().setName("bevis").build()).build()
//                        ).build())
//                .build();
//
//        Session session = Session.create(sessionCreateParams);
//        return session.getUrl();

//        long totalAmount = Integer.parseInt(amount + "000") * 100L;


        long totalAmount = amount * 100L;


        Map<String, String> vnpParamsMap = vnPayConfig.getVNPayConfig();
        vnpParamsMap.put("vnp_Amount", String.valueOf(totalAmount));
//        vnpParamsMap.put("vnp_OrderInfo", "Payment for Order #" + orderId);

//        vnpParamsMap.put("name", user.getFullName());
//        vnpParamsMap.put("email", user.getEmail());


//        vnpParamsMap.put("vnp_CurrCode", "VND");
//        vnpParamsMap.put("vnp_Locale", "vn");
        vnpParamsMap.put("vnp_ReturnUrl", "http://localhost:3000/payment-success/" + orderId);


        if (bankCode != null && !bankCode.isEmpty()) {
            vnpParamsMap.put("vnp_BankCode", bankCode);
        }

        vnpParamsMap.put("vnp_IpAddr", VNPayUtil.getIpAddress(req));
        //build query url
        String queryUrl = VNPayUtil.getPaymentURL(vnpParamsMap, true);
        String hashData = VNPayUtil.getPaymentURL(vnpParamsMap, false);

        String vnpSecureHash = VNPayUtil.hmacSHA512(vnPayConfig.getSecretKey(), hashData);
        queryUrl += "&vnp_SecureHash=" + vnpSecureHash;

        return vnPayConfig.getVnp_PayUrl() + "?" + queryUrl;
    }
}
