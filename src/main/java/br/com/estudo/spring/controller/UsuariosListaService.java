package br.com.estudo.spring.controller;

import java.io.IOException;
import java.util.List;

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
import br.com.estudo.spring.model.Usuario;

import com.google.gson.Gson;

@WebServlet("/usuarios.json")
public class UsuariosListaService extends HttpServlet {
	
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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/json");
		
		List<Usuario> lista = usuarioDao.buscarTodos();
		Gson gson = new Gson();
		String result= gson.toJson(lista);
		resp.getWriter().println(result);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String id = req.getParameter("id");
		Usuario usuario = new Usuario();
		if (id != null)
			usuario.setId(Integer.parseInt(id));
		
		usuarioDao.excluir(usuario);
		resp.getWriter().println("Sucesso. O usuário foi removido.");
	}
}
