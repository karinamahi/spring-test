package br.com.estudo.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.estudo.spring.model.Cliente;

public class ClienteDao {

	private final DataSource dataSource;
	private Connection connection;

	public ClienteDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void addCliente(Cliente cliente) {
		String sql = "insert into cliente" + "(nome, cpf)" + "values(?,?)";

		try {
			connection = dataSource.getConnection();
			java.sql.PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, cliente.getNome());
			stmt.setString(2, cliente.getCpf());

			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private Cliente convertResultSet2Cliente(ResultSet result){
		try {
			Cliente cliente = new Cliente();
			cliente.setNome(result.getString("nome"));
			cliente.setCpf(result.getString("cpf"));
			return cliente;
			
		} catch (SQLException e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Cliente> getLista(){
		try{
			connection = dataSource.getConnection();
			List<Cliente> clientes = new ArrayList<Cliente>();
			PreparedStatement stmt = (PreparedStatement) this.connection.prepareStatement("select * from cliente");
			
			ResultSet rs = stmt.executeQuery();
			while (rs.next()){
				Cliente cliente = convertResultSet2Cliente(rs);
				clientes.add(cliente);
			}
			rs.close();
			stmt.close();
			return clientes;
		}catch (SQLException e){
			throw new RuntimeException(e);
		}
	}
}
