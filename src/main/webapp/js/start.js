console.log("Hello World");
	$("#h2").text("Hi");
	
	function excluir(id){
		$.ajax({
			url:'/usuarios.json?id='+id,
			type: 'DELETE',
			success: function(result){
				console.log(result);
				exibir();
			}
		})
	}
	
	function exibir(){
	$.get( "/usuarios.json", function(data) {
		 console.log(data);
		 $("ul li").remove();
 		 for (var int = 0; int < data.length; int++) {
 			 var usuario=data[int];
 		 	$("<li>"+usuario.nome+": "+usuario.email+" "+"<a onclick=\"excluir("+usuario.id+")\">"+"remover"+"</a>"+"</li>").appendTo("ul")
 		}
		});
	}
	
	