package orishop.controllers.user;

import java.io.IOException;


import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import orishop.models.CartItemModels;
import orishop.models.CartModels;
import orishop.services.CartItemServiceImpl;
import orishop.services.CartServiceImpl;
import orishop.services.ICartItemService;
import orishop.services.ICartService;
@WebServlet(urlPatterns = { "/user/findCartbyCartId", "/user/findCartItem", "/user/insertCartItem", "/user/updateCartItem", "/user/deleteCartItem", "/user/countCartItem"})

public class UserCartController extends HttpServlet  {
private static final long serialVersionUID = 1L;
	
	ICartService cartService = new CartServiceImpl();
	ICartItemService cartItemService = new CartItemServiceImpl();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String url = req.getRequestURI().toString();
		if (url.contains("user/findCartbyCardID")) {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");

			String cartId = req.getParameter("cartId");

			CartModels cart = cartService.findCartByCartID(Integer.parseInt(cartId));
			req.setAttribute("cart", cart);

			List<CartItemModels> listCartItem = cartItemService.findCartItemByCartID(Integer.parseInt(cartId));
			req.setAttribute("listCartItem", listCartItem);

			req.getRequestDispatcher("/views/user/cart.jsp").forward(req, resp);
		} 
		
		else if (url.contains("user/deleteCartItem")) {
			req.setCharacterEncoding("UTF-8");
			resp.setCharacterEncoding("UTF-8");

			int cartId = Integer.parseInt(req.getParameter("cartId"));
			int productId = Integer.parseInt(req.getParameter("productId"));

			cartItemService.deleteCartItem(cartId, productId);

			req.setAttribute("message", "Đã xóa thành công");

			RequestDispatcher rd = req.getRequestDispatcher("/views/user/cart.jsp");
			rd.forward(req, resp);
		} 
		
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI().toString();
		if (url.contains("user/insertCartItem")) {
			insertCartItem(req, resp);
		} else if (url.contains("user/updateCartItem")) {
			updateCartItem(req, resp);
		}
	}
	private void updateCartItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		int cartId = Integer.parseInt(req.getParameter("cartId"));
		int productId = Integer.parseInt(req.getParameter("productId"));
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		float totalPrice = Float.parseFloat(req.getParameter("totalPrice"));

		CartItemModels cartItem = new CartItemModels();
		cartItem.setCartID(cartId);
		cartItem.setProductID(productId);;
		cartItem.setQuantity(quantity);
		cartItem.setTotalPrice(totalPrice);

		cartItemService.updateCartItem(cartItem);
		resp.sendRedirect(req.getContextPath() + "/user/findCartItem");

	}

	private void insertCartItem(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");

		int cartId = Integer.parseInt(req.getParameter("cartId"));
		int productId = Integer.parseInt(req.getParameter("productId"));
		int quantity = Integer.parseInt(req.getParameter("quantity"));
		float totalPrice = Float.parseFloat(req.getParameter("totalPrice"));
		
		CartItemModels model = cartItemService.findCartItemByProductID(cartId, productId);
		if (model == null) {
			CartItemModels cartItem = new CartItemModels();
			cartItem.setCartID(cartId);
			cartItem.setProductID(productId);;
			cartItem.setQuantity(quantity);
			cartItem.setTotalPrice(totalPrice);
			cartItemService.insertCartItem(cartItem);
		} else {
			CartItemModels cartItem = new CartItemModels();
			cartItem.setCartID(cartId);
			cartItem.setProductID(productId);;
			cartItem.setQuantity(quantity + model.getQuantity());
			cartItem.setTotalPrice(totalPrice);
			cartItemService.updateCartItem(cartItem);
		}
	}
}




