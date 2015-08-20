package br.com.estudo.spring.dao.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import br.com.estudo.spring.dao.ClienteDao;
import br.com.estudo.spring.model.Cliente;

public class ClienteDaoTest {

	private ClienteDao dao;

	@Before
	public void carregandoSpring() {
		ApplicationContext context = new ClassPathXmlApplicationContext(
				"beans/spring-beans.xml");
		Assert.assertNotNull(context);
		dao = context.getBean("clienteDao", ClienteDao.class);
		Assert.assertNotNull(dao);
	}

	@Test
	public void deveAdicionarUmCliente() {
		Cliente cliente = new Cliente();
		cliente.setNome("Karen");
		cliente.setCpf("321");
		dao.addCliente(cliente);
		System.out.println("Cliente adicionado com sucesso!");
	}

	@Test
	public void deveRetornarTodosOsClientes() {
		List<Cliente> clientes = dao.getLista();
		Assert.assertNotNull(clientes);
		Assert.assertTrue(clientes.size() > 0);
		for (Cliente cliente : clientes) {
			System.out.println("NOME: " + cliente.getNome());
			System.out.println("CPF: " + cliente.getCpf() + "\n");
		}
	}

}
