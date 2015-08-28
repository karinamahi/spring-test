package br.com.estudo.spring.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.RequestDispatcher;
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


	//http://localhost:8080/spring-test/usuariocontroller.do
	@WebServlet("/usuariocontroller.do")
	public class UsuarioController extends HttpServlet {

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
		protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			resp.setContentType("text/html");
			String acao = req.getParameter("acao");
			if ("excluir".equalsIgnoreCase(acao)) {
				String id = req.getParameter("id");
				Usuario usuario = new Usuario();
				if (id != null)
					usuario.setId(Integer.parseInt(id));

				usuarioDao.excluir(usuario);
				//resp.getWriter().print("<b>Excluído com sucesso!</b>");
				resp.sendRedirect("usuariocontroller.do?acao=listar");
				System.out.println("Chamou!" + req);
				
			} else if ("listar".equalsIgnoreCase(acao)) {
				List<Usuario> lista = usuarioDao.buscarTodos();

				req.setAttribute("lista", lista);
				RequestDispatcher dispatcher = req
						.getRequestDispatcher("WEB-INF/listausu.jsp");
				dispatcher.forward(req, resp);
				
			} else if("alt".equalsIgnoreCase(acao)){
				String id = req.getParameter("id");
				Usuario usuario = usuarioDao.buscarPorId(Integer.parseInt(id));
				req.setAttribute("usu", usuario);
				RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/usuarioform.jsp");
				dispatcher.forward(req, resp);
				
			}else if("cad".equalsIgnoreCase(acao)){
				Usuario usuario = new Usuario();
				usuario.setId(0);
				usuario.setCpf("");
				usuario.setNome("");
				usuario.setRg("");
				usuario.setEmail("");
				usuario.setPerfil("");
				usuario.setSenha("");
				
				req.setAttribute("usu", usuario);
				RequestDispatcher dispatcher = req.getRequestDispatcher("WEB-INF/usuarioform.jsp");
				dispatcher.forward(req, resp);
			}
		}

		protected void doPost(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException {
			System.out.println("Chamou!");
			String id = req.getParameter("id");
			String nome = req.getParameter("nome");
			String email = req.getParameter("email");
			String senha = req.getParameter("senha");
			String cpf = req.getParameter("cpf");
			String rg = req.getParameter("rg");
			String perfil = req.getParameter("perfil");

			Usuario usuario = new Usuario();
			if (id != "")
				usuario.setId(Integer.parseInt(id));

			usuario.setNome(nome);
			usuario.setEmail(email);
			usuario.setSenha(senha);
			usuario.setCpf(cpf);
			usuario.setRg(rg);
			usuario.setPerfil(perfil);

			usuarioDao.salvar(usuario);
			resp.getWriter().print("<b>Sucesso!</b>");

			StringBuffer jb = new StringBuffer();
			String line = null;
			try {
				BufferedReader reader = req.getReader();
				while ((line = reader.readLine()) != null)
					jb.append(line);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		@Override
		public void destroy() {
			System.out.println("Destroy..");
			super.destroy();
		}
	}


