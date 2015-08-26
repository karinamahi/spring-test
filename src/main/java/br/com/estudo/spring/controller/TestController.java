package br.com.estudo.spring.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.estudo.spring.dao.UsuarioDao;

@WebServlet("/testcontroller.do")
public class TestController extends HttpServlet{
	
	protected AutowireCapableBeanFactory ctx;
	
	@Override
	public void init() throws ServletException {
		super.init();
		Enumeration<String> names = getServletContext().getAttributeNames();
		while(names.hasMoreElements()){
			System.out.println(names.nextElement());
		}
		WebApplicationContext requiredWebApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		//ApplicationContext appCtx = (ApplicationContext) getServletContext().getAttribute("applicationContext");
		//ctx = appCtx.getAutowireCapableBeanFactory();
		ctx=requiredWebApplicationContext.getAutowireCapableBeanFactory();
		ctx.autowireBean(this);
	}
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println(usuarioDao);
		req.getRequestDispatcher("WEB-INF/index.jsp").forward(req, resp);
	}
	
	public UsuarioDao getUsuarioDao() {
		return usuarioDao;
	}
	
	public void setUsuarioDao(UsuarioDao usuarioDao) {
		this.usuarioDao = usuarioDao;
	}
	
}
