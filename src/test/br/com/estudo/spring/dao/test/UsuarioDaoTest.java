package br.com.estudo.spring.dao.test;


import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.estudo.spring.dao.UsuarioDao;
import br.com.estudo.spring.model.Usuario;

public class UsuarioDaoTest {
	
	private UsuarioDao dao;
	
	
	@Before
	public void carregandoSpring(){
		ApplicationContext context = new ClassPathXmlApplicationContext("beans/spring-beans.xml");
		Assert.assertNotNull(context);
		dao = context.getBean("usuarioDao", UsuarioDao.class);
		Assert.assertNotNull(dao);
	}
	
	@Test
	public void deveAdicionarUmUsuario() {
		Usuario usuario = new Usuario();
		usuario.setCpf("999");
		usuario.setRg("99");
		usuario.setNome("Jao");
		usuario.setEmail("ze@test.com");
		usuario.setPerfil("Analista");
		usuario.setSenha("999");

		
		dao.addUsuario(usuario);

		System.out.println("Usuário adicionado com sucesso!");
	}
}
