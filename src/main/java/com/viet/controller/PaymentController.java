package com.viet.controller;

import com.stripe.exception.StripeException;
import com.viet.model.*;
import com.viet.response.ApiResponse;
import com.viet.response.PaymentResponse;
import com.viet.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PaymentController {

    PaymentService paymentService;
    UserService userService;
    SellerService sellerService;
    OrderService orderService;
    SellerReportService sellerReportService;
    TransactionService transactionService;

    @GetMapping("/{paymentId}")
    public ResponseEntity<ApiResponse> paymentSuccess(@PathVariable String paymentId,
                                                      @RequestHeader("Authorization") String jwt,
                                                      @RequestParam String paymentLinkId) throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        PaymentResponse response;

        PaymentOrder paymentOrder = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

        for(Order order : paymentOrder.getOrders()) {
            transactionService.createTransaction(order);
            Seller seller = sellerService.getSellerById(order.getSellerId());

            SellerReport sellerReport = sellerReportService.getSellerReport(seller);
            sellerReport.setTotalOrders(sellerReport.getTotalOrders() + 1);
            sellerReport.setTotalEarnings(sellerReport.getTotalEarnings() + order.getTotalSellingPrice());
            sellerReport.setTotalSales(sellerReport.getTotalSales() + order.getOrderItems().size());

            sellerReportService.updateSellerReport(sellerReport);
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("Payment Success");

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);

    }
}
