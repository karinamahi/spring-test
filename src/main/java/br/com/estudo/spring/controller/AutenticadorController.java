package br.com.estudo.spring.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import br.com.estudo.spring.dao.UsuarioDao;
import br.com.estudo.spring.model.Usuario;


@WebServlet("/autenticador.do")
public class AutenticadorController extends HttpServlet {
	
	protected AutowireCapableBeanFactory ctx;
	
	@Override
	public void init() throws ServletException {
		super.init();
		
		WebApplicationContext requiredWebApplicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		
		ctx=requiredWebApplicationContext.getAutowireCapableBeanFactory();
		ctx.autowireBean(this);
	}
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// 1) Capturando dados da tela
		String email = req.getParameter("email");
		String senha = req.getParameter("senha");
		
		// 2) Colocando dados em um objeto Usuario
		Usuario usuario = new Usuario();
		usuario.setEmail(email);
		usuario.setSenha(senha);
		
		// 3)Consultando se usuario existe no banco
			
		Usuario usuAutenticado = usuarioDao.autenticar(usuario);
		
		// 4) Verificando se o usuario foi encontrado
		if(usuAutenticado!=null){
			
			// 5) Colocando usuario na sessao
			HttpSession sessao = req.getSession();
			sessao.setAttribute("usuAutenticado",usuAutenticado);
			sessao.setMaxInactiveInterval(60*5);
			
			// 6) Redirecionando usuario para tela principal
			req.getRequestDispatcher("index.jsp").forward(req, resp);
		}else {
			resp.getWriter().print("<script> window.alert ('Usuário não encontrado!'); location.href='login.html'</script>");
			
		}
		
	}
}
