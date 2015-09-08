<html>
<body>
<h2>Bem-vindo!</h2>
<div class="h2">eu sou uma div</div>
<div id="h2">meu nome é h2</div>
<button onclick="exibir();">Exibir usuários</button>
<ul>
</ul>
<script src="http://code.jquery.com/jquery-2.1.4.min.js"></script>
<script type="text/javascript" src="js/start.js">
</script>

<script type="text/javascript">
var chris = {
		nome:"Chris Martin",
		idade: 35,
		banda: "ColdPlay",
		instrumento: ["piano","violão"],
		funcao: ["vocalista", "pianista"],
		hasFuncao: function(funcao){
			return this.funcao.indexOf(funcao)>=0;
		}
}

var jonny = {
		nome: "Jonny Buckland",
		idade: 35,
		banda: "ColdPlay",
		instrumento: ["guitarra"],
		funcao: ["guitarrista"],
		hasFuncao: function(funcao){
			return this.funcao.indexOf(funcao)>=0;
		}
}

var banda = {
		nome:"ColdPlay",
		origem: {
			local: "Londres",
			ano: 1996
		},
		estilo: "pop-rock",
		integrantes: [chris, jonny],
		getByFuncao: function(funcao){
			var result = [];
			for (var int = 0; int < this.integrantes.length; int++) {
				var integrante = this.integrantes[int];
				if(integrante.hasFuncao(funcao)){
					result.push(integrante);
				}
			}
			return result;
		}
}


var res = banda.getByFuncao("vocalista");
console.log(res);

console.log(banda);
</script>
</body>
</html>
