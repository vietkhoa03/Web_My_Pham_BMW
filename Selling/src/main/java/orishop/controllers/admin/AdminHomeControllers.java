//1
package orishop.controllers.admin;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import orishop.DAO.CustomerDAOImp;
import orishop.DAO.IEmployeeDAO;
import orishop.models.CategoryModels;
import orishop.models.CustomerModels;
import orishop.models.EmployeeModels;
import orishop.services.CategoryServiceImp;
import orishop.services.CustomerServiceImp;
import orishop.services.EmployeeServiceImp;
import orishop.services.ICategoryService;
import orishop.services.ICustomerService;
import orishop.services.IEmployeeService;
import orishop.services.IOrderService;
import orishop.services.OrderServiceImpl;

@WebServlet(urlPatterns = {"/admin/home", "/admin/listoder"})

public class AdminHomeControllers extends HttpServlet {
	ICategoryService cateService = new CategoryServiceImp();
	IEmployeeService empService = new EmployeeServiceImp();
	ICustomerService cusService = new CustomerServiceImp();
	IOrderService orderService = new OrderServiceImpl();
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String url = req.getRequestURI();
		if(url.contains("admin/home")) {
			//findAllShipper(req, resp);
			getHomeAdmin(req, resp);
		}
	}
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		
	}
	
	
	//region shipper
	private void findAllShipper(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String search = req.getParameter("search_info");
		
		List<EmployeeModels> listEmp;
		if(search == null || search.isBlank()) {
			listEmp = empService.findAllShipper();
		} else {
			EmployeeModels emp;
			try {
				emp = empService.findShipper(Integer.valueOf(search));
			} catch (Exception e){
				emp = empService.findShipper(search).get(0);
			}
			listEmp = new ArrayList<EmployeeModels>();
			listEmp.add(emp);
		}
		int pagesize = 3;
		int size = listEmp.size();
		int num = (size%pagesize==0 ? (size/pagesize) : (size/pagesize + 1));
		int page, numberpage = pagesize;
		String xpage = req.getParameter("page");
		if (xpage == null) {
			page = 1;
		}
		else {
			page = Integer.parseInt(xpage);
		}
		int start,end;
		start = (page - 1) * numberpage;
		end = Math.min(page*numberpage, size);
		
		List<EmployeeModels> list = empService.getListEmpByPage(listEmp, start, end);
		req.setAttribute("list", list);
		req.setAttribute("page", page);
		req.setAttribute("num", num);
		req.setAttribute("count", listEmp.size());
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/control_shipper/listshipper.jsp");
		rd.forward(req, resp);
	}
	//endregion
		
	
	//region order
	private void getHomeAdmin(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		long earningmonthly = orderService.totalRevenueByMonth(1);
		long earningannual = orderService.totalRevenueByYear(2023);
		long totalearning = orderService.totalPriceProductSell();
		int orderrequest = orderService.countOrderRequest();
		
		ObjectMapper objectMapper = new ObjectMapper();
		Object[] revenue = orderService.thongke(2023);
		String revenueJSON = objectMapper.writeValueAsString(revenue);
		revenueJSON = URLEncoder.encode(revenueJSON, "UTF-8");
		
		req.setAttribute("revenueJSON", revenueJSON);
		req.setAttribute("earningmonthly", earningmonthly);
		req.setAttribute("earningannual", earningannual);
		req.setAttribute("totalearning", totalearning);
		req.setAttribute("orderrequest", orderrequest);
		RequestDispatcher rd = req.getRequestDispatcher("/views/admin/home.jsp");
		rd.forward(req, resp);
	}
	//endregion	
}
