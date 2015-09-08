<%@page import="br.com.estudo.spring.model.Usuario"%>
<%@ page language="java" contentType="text/html;  charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;  charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<% Usuario u = (Usuario)request.getAttribute("usu");
	%>
	

	<form action="usuariocontroller.do" method="post">
		ID: <input type="number" name="id" value="<%=u.getId()%>"/>
		Nome: <input type="text" name="nome" value="<%=u.getNome()%>"/>
		Email: <input type="text" name="email" value="<%=u.getEmail()%>"/>
		Senha: <input type="text" name="senha" value="<%=u.getSenha()%>"/>
		CPF: <input type="text" name="cpf" value="<%=u.getCpf()%>"/>
		Perfil: <input type="text" name="perfil" value="<%=u.getPerfil()%>"/>
		RG: <input type="text" name="rg" value="<%=u.getRg()%>"/>
		<input type="submit" value="Salvar">
	</form>	
</body>
</html>