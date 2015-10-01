package br.com.estudo.spring.listerners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import br.com.estudo.spring.model.Usuario;

//@WebListener - usar annotation quando não configurar no web.xml
public class MeuListener implements ServletContextListener {

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Iniciooooooooooooooooooooooooooooooooooou");
		 Usuario usuario = new Usuario();
		 usuario.setNome("Listener");
		sce.getServletContext().setAttribute("teste", usuario);
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}
	
}
