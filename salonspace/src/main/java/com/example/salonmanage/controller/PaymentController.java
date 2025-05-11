package com.example.salonmanage.controller;

import com.example.salonmanage.DTO.BookDTO;
import com.example.salonmanage.DTO.TransactionStatusDTO;
import com.example.salonmanage.Entities.*;
import com.example.salonmanage.config.PaymentConfig;
import com.example.salonmanage.model.PaymentRes;
import com.example.salonmanage.repository.*;
import com.example.salonmanage.service.BookingDetailService;
import com.example.salonmanage.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class PaymentController {
    @Autowired
    private BookingDetailService bookingDetailService;
    @Autowired
    private TimesRepository timesRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private ServiceRepository serviceRepository;
    @Autowired
    private BookingDetailRepository bookingDetailRepository;
    @Autowired
    private BranchRepository branchRepository;
    @PostMapping("/checkout/create-payment")
    public ResponseEntity<?> createPayment(@RequestBody BookDTO requestParams) {
        // String TXNREF = PaymentConfig.getRandomNumber(5);
        User user = userService.findByPhone(requestParams.getUser());
        Booking booking = new Booking();

        booking.setDate(requestParams.getDate());
        booking.setNhanvien(requestParams.getNhanvien());
        booking.setUser(user);
        Times times = timesRepository.getById(requestParams.getTime());
        booking.setTimes(times);
        Branch branch = branchRepository.getById(requestParams.getBranch());
        booking.setBranch(branch);
        booking.setTotalPrice(requestParams.getTotalPrice());
        booking.setDiscount(0);
        booking.setStatus(0);
        booking.setPayment(0);
        Booking booking1= bookingRepository.save(booking);
        List<BookingDetail> list= bookingDetailRepository.findByBookingId(user.getId());
        for (BookingDetail b:list
        ) {
            b.setBooking(booking1);
            b.setStatus(1);
            bookingDetailRepository.save(b);
        }

//        Order order = orderRepository.findByOrderId(requestParams.getOrderId());

        Long amount = booking1.getTotalPrice() * 100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.VERSIONVNPAY);
        vnp_Params.put("vnp_Command", PaymentConfig.COMMAND);
        vnp_Params.put("vnp_TmnCode", PaymentConfig.TMNCODE);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", PaymentConfig.CURRCODE);
        // if (requestParams.getBankCode() != null &&
        // !requestParams.getBankCode().isEmpty()) {
        vnp_Params.put("vnp_BankCode", requestParams.getBankCode());
        vnp_Params.put("vnp_Locale", PaymentConfig.LOCALEDEFAULT);
        // vnp_Params.put("vnp_CardType", PaymentConfig.CARDTYPE);
        vnp_Params.put("vnp_TxnRef", String.valueOf(booking1.getID()));
        vnp_Params.put("vnp_OrderInfo", "Thanh toan hoa don" + booking1.getID());
        vnp_Params.put("vnp_OrderType", PaymentConfig.ORDERTYPE);
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.RETURNURL);
        vnp_Params.put("vnp_IpAddr", PaymentConfig.IPDEFAULT);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
                // Build query
                try {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
                query.append('=');
                try {
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.CHECKSUM, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.VNPAYURL + "?" + queryUrl;
        PaymentRes result = new PaymentRes();
        result.setStatus("00");
        result.setMessage("Success");
        result.setUrl(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("/checkout/re-create-payment")
    public ResponseEntity<?> createPayment(@RequestParam int bookingid) {
        Booking booking1 = bookingRepository.getById(bookingid);
        User user = booking1.getUser();
        Booking booking = new Booking();

//        Order order = orderRepository.findByOrderId(requestParams.getOrderId());

        Long amount = booking1.getTotalPrice() * 100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.VERSIONVNPAY);
        vnp_Params.put("vnp_Command", PaymentConfig.COMMAND);
        vnp_Params.put("vnp_TmnCode", PaymentConfig.TMNCODE);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", PaymentConfig.CURRCODE);
        // if (requestParams.getBankCode() != null &&
        // !requestParams.getBankCode().isEmpty()) {
        vnp_Params.put("vnp_BankCode", "NCB");
        vnp_Params.put("vnp_Locale", PaymentConfig.LOCALEDEFAULT);
        // vnp_Params.put("vnp_CardType", PaymentConfig.CARDTYPE);
        vnp_Params.put("vnp_TxnRef", String.valueOf(booking1.getID()));
        vnp_Params.put("vnp_OrderInfo", "Thanh toan hoa don" + booking1.getID());
        vnp_Params.put("vnp_OrderType", PaymentConfig.ORDERTYPE);
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.RETURNURL);
        vnp_Params.put("vnp_IpAddr", PaymentConfig.IPDEFAULT);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                try {
                    hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
                // Build query
                try {
                    query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
                query.append('=');
                try {
                    query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                } catch (UnsupportedEncodingException e) {

                    e.printStackTrace();
                }
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.CHECKSUM, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.VNPAYURL + "?" + queryUrl;
        PaymentRes result = new PaymentRes();
        result.setStatus("00");
        result.setMessage("Success");
        result.setUrl(paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/checkout/payment-information")
    public ResponseEntity<?>  transactionHandle (
            @RequestParam(value = "vnp_Amount", required = false) String amount,
            @RequestParam(value = "vnp_BankCode", required = false) String bankCode,
            @RequestParam(value = "vnp_BankTranNo", required = false) String bankTranNo,
            @RequestParam(value = "vnp_CardType", required = false) String cardType,
            @RequestParam(value = "vnp_PayDate", required = false) String payDate,
            @RequestParam(value = "vnp_OrderInfo", required = false) String orderInfo,
            @RequestParam(value = "vnp_ResponseCode", required = false) String responseCode,
            @RequestParam(value = "vnp_TransactionNo", required = false) String transactionNo,
            @RequestParam(value = "vnp_TransactionStatus" ,required = false) String transactionStatus,
            @RequestParam(value = "vnp_TxnRef", required = false) String txnRef,
            @RequestParam(value = "vnp_SecureHash", required = false) String secureHash,
            @RequestParam(value = "vnp_SecureHashType", required = false) String secureHashType
    ) throws MessagingException {
        TransactionStatusDTO result = new TransactionStatusDTO();

        Booking booking = bookingRepository.getById(Integer.parseInt(txnRef));
        if(booking.getID().toString().equalsIgnoreCase(txnRef)) {
            System.out.println(transactionStatus);
            if(transactionStatus.equals("00")) {
                booking.setPayment(1);
                booking.setStatus(2);
            }
            bookingRepository.save(booking);
            result.setStatus("00");
            result.setMessage("Checkout successfully");
            var uri = UriComponentsBuilder.fromHttpUrl("http://salonspace.io.vn/")
                    .build();
            return ResponseEntity.status(HttpStatus.FOUND).location(uri.toUri()).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
