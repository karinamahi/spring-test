package br.com.estudo.spring.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import br.com.estudo.spring.model.Usuario;

public class UsuarioDao {
	
	private final DataSource dataSource;
	private  Connection connection;

	public UsuarioDao(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void addUsuario(Usuario usuario) {
		String sql = "insert into usuario"
				+ "(cpf, rg, nome, email, perfil, senha)"
				+ "values(?,?,?,?,?,md5(?))";

		try {
			connection=dataSource.getConnection();
			java.sql.PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, usuario.getCpf());
			stmt.setString(2, usuario.getRg());
			stmt.setString(3, usuario.getNome());
			stmt.setString(4, usuario.getEmail());
			stmt.setString(5, usuario.getPerfil());
			stmt.setString(6, usuario.getSenha());

			stmt.execute();
			stmt.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	private Usuario convertResultSet2Usuario(ResultSet result) {
		try {
			Usuario usuario = new Usuario();
			usuario.setCpf(result.getString("cpf"));
			usuario.setNome(result.getString("nome"));
			usuario.setRg(result.getString("rg"));
			usuario.setPerfil(result.getString("perfil"));
			usuario.setEmail(result.getString("email"));
			return usuario;

		} catch (SQLException e) {
			System.out.println("Erro");
			e.printStackTrace();
		}
		return null;
	}

	public List<Usuario> getLista() {
		try {
			connection=dataSource.getConnection();
			List<Usuario> usuarios = new ArrayList<Usuario>();
			PreparedStatement stmt = (PreparedStatement) this.connection
					.prepareStatement("select * from usuario");

			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Usuario usuario = convertResultSet2Usuario(rs);
				usuarios.add(usuario);
			}
			rs.close();
			stmt.close();
			return usuarios;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void ImprimiUsuarios() {
		List<Usuario> usuarios = getLista();

		for (Usuario usuario : usuarios) {
			System.out.println("CPF: " + usuario.getCpf());
			System.out.println("NOME: " + usuario.getNome());
			System.out.println("RG: " + usuario.getRg());
			System.out.println("EMAIL: " + usuario.getEmail());
			System.out.println("PERFIL: " + usuario.getPerfil() + "\n");
		}
	}

	public void salvar(Usuario usuario) {
		if (usuario.getId() != null && usuario.getId()!=0) {
			alterarUsuario(usuario);
		} else {
			addUsuario(usuario);
		}
	}

	public void alterarUsuario(Usuario usuario) {
		String sql = "update usuario set cpf=?, rg=?, nome=?, email=?, perfil=?, senha=md5(?) where id=?";

		try {
			connection=dataSource.getConnection();
			java.sql.PreparedStatement stmt = connection.prepareStatement(sql);

			stmt.setString(1, usuario.getCpf());
			stmt.setString(2, usuario.getRg());
			stmt.setString(3, usuario.getNome());
			stmt.setString(4, usuario.getEmail());
			stmt.setString(5, usuario.getPerfil());
			stmt.setString(6, usuario.getSenha());
			stmt.setInt(7, usuario.getId());
			stmt.execute();
			stmt.close();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Faz busca de um registro no banco de dados pelo número do id do usuário
	 * 
	 * @param id
	 *            É um inteiro que representa o número do id do usuário a ser
	 *            buscado
	 * @return Um objeto usuário quando encontra ou null quando não encontra
	 */
	public Usuario buscarPorId(Integer id) {
		String sql = "Select * from usuario where id=?";
		try {
			connection=dataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setEmail(rs.getString("email"));
				usuario.setNome(rs.getString("nome"));
				usuario.setPerfil(rs.getString("perfil"));
				usuario.setRg(rs.getString("rg"));
				usuario.setSenha(rs.getString("senha"));
				return usuario;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Realiza a busca de todos os registros da tabela usuario
	 * @return Uma lista de objetos Usuario
	 */
	public List<Usuario> buscarTodos() {
		String sql = "Select * from usuario";
		List<Usuario> lista = new ArrayList<Usuario>();
		try {
			connection=dataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			while (rs.next()) {
				Usuario usuario = new Usuario();
				usuario.setId(rs.getInt("id"));
				usuario.setCpf(rs.getString("cpf"));
				usuario.setEmail(rs.getString("email"));
				usuario.setNome(rs.getString("nome"));
				usuario.setPerfil(rs.getString("perfil"));
				usuario.setRg(rs.getString("rg"));
				usuario.setSenha(rs.getString("senha"));
				lista.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	public Usuario autenticar(Usuario consultaUsuario){
		String sql = "Select * from usuario where email=? and senha=md5(?)";
		
		try {
			this.connection = dataSource.getConnection();
			PreparedStatement stmt = connection.prepareStatement(sql);
			stmt.setString(1, consultaUsuario.getEmail());
			stmt.setString(2, consultaUsuario.getSenha());
			ResultSet rs = stmt.executeQuery();
			if(rs.next()){
				Usuario usuario = new Usuario();
			usuario.setNome(rs.getString("nome"));
			usuario.setEmail(rs.getString("email"));
			usuario.setCpf(rs.getString("cpf"));
			usuario.setRg(rs.getString("rg"));
			usuario.setPerfil(rs.getString("perfil"));
			usuario.setId(rs.getInt("id"));
			usuario.setSenha(rs.getString("senha"));
			return usuario;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;		
	}
	
	public void excluir(Usuario usuario){
		try {
			PreparedStatement stmt = (PreparedStatement) connection.prepareStatement("delete from usuario where id=?");
			stmt.setInt(1, usuario.getId());
			stmt.execute();
			stmt.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}


