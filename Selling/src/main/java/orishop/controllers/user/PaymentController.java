package orishop.controllers.user;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import orishop.models.CartItemModels;
import orishop.services.CartItemServiceImpl;
import orishop.services.CartServiceImpl;
import orishop.services.CategoryServiceImp;
import orishop.services.CustomerServiceImp;
import orishop.services.EmployeeServiceImp;
import orishop.services.ICartItemService;
import orishop.services.ICartService;
import orishop.services.ICategoryService;
import orishop.services.ICustomerService;
import orishop.services.IEmployeeService;
import orishop.services.IOrderService;
import orishop.services.IProductService;
import orishop.services.OrderServiceImpl;
import orishop.services.ProductServiceImp;
import orishop.util.Config;

@WebServlet(urlPatterns = { "/user/pay","/paymentInfo", "/user/error","/user/thanks"})

public class PaymentController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	ICartService cartService = new CartServiceImpl();
	ICartItemService cartItemService = new CartItemServiceImpl();
	ICustomerService CustomerSerivce = new CustomerServiceImp();
	IEmployeeService empService = new EmployeeServiceImp();
	IOrderService orderService = new OrderServiceImpl();
	
	IProductService productService = new ProductServiceImp();
	ICategoryService categoryService = new CategoryServiceImp();

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String url = req.getRequestURI().toString();

		if (url.contains("user/pay")) {
			try {
				String paymentUrl = getPay(req);
				resp.sendRedirect(paymentUrl);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				resp.sendRedirect("/error");
			}
		} else if (url.contains("paymentInfo")) {
			paymentInfo(req, resp);
		}

	}

	public void paymentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		String amount = req.getParameter("vnp_Amount");
		String bankCode = req.getParameter("vnp_BankCode");
		String orderId = req.getParameter("vnp_TxnRef");
		String responseCode = req.getParameter("vnp_ResponseCode");

		
		int orderID = Integer.parseInt(orderId);
		
		if (responseCode.equals("00")) {
			String paymentStatus = "paid";
			orderService.updateOrderPaymentStatus(orderID, paymentStatus);
			req.getRequestDispatcher("/views/user/inforuser_cart/complete.jsp").forward(req, resp);
		} else {
			req.getRequestDispatcher("/views/user/inforuser_cart/paymentFailure.jsp").forward(req, resp);
		}
		
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

	}

	public String getPay(HttpServletRequest req) throws UnsupportedEncodingException {

		String vnp_Version = "2.1.0";
		String vnp_Command = "pay";
		String orderType = "other";
		String bankCode = "";
		
		HttpSession session = req.getSession();
		int orderID = orderService.findLatestOrderId();
		
		String order_id = Integer.toString(orderID);
		float totalPriceOrder = (float)session.getAttribute("totalPriceOrder");
		
		
		long amount = (long)totalPriceOrder*100; // Số tiền đơn hàng
		
		String vnp_TxnRef = order_id;
		String vnp_IpAddr = "127.0.0.1";

		String vnp_TmnCode = Config.vnp_TmnCode;

		Map<String, String> vnp_Params = new HashMap<>();
		vnp_Params.put("vnp_Version", vnp_Version);
		vnp_Params.put("vnp_Command", vnp_Command);
		vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
		vnp_Params.put("vnp_Amount", String.valueOf(amount));
		vnp_Params.put("vnp_CurrCode", "VND");

		vnp_Params.put("vnp_BankCode", bankCode);
		vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
		vnp_Params.put("vnp_OrderInfo", "Thanh toan don hang:" + vnp_TxnRef);
		vnp_Params.put("vnp_OrderType", orderType);

		vnp_Params.put("vnp_Locale", "vn");
		vnp_Params.put("vnp_ReturnUrl", Config.vnp_ReturnUrl);
		vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

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
				hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				// Build query
				query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
				query.append('=');
				query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
				if (itr.hasNext()) {
					query.append('&');
					hashData.append('&');
				}
			}
		}
		String queryUrl = query.toString();
		String vnp_SecureHash = Config.hmacSHA512(Config.secretKey, hashData.toString());
		queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
		String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

		return paymentUrl;
	}
}
