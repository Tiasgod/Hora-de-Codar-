console.log("olá mundo!")
 
 
let idade = 18
let nome= 'Maria'
let status = true
let altura= 1.78
let vazia = null
let indefinida;
 
console.log(idade, altura, nome, status, indefinida)
console.log(typeof idade, typeof altura, typeof nome,  typeof status, typeof vazio, typeof indefinida)
const dia = 6 //const= abreviação de constante: é definido valores fixos na declaração da varável e não permite a re declarção do valor
console.log(dia)
 
//pq não usar ao var
 
function exemplo(){
    console.log("eu sou uma função")
}
 
//usando função
exemplo()
 
    function soma (a,b){
        return a+b
    }
 
let resultado = soma (10,10)
console.log(resultado)
console.log(soma(5,5))
   
function exemplo_let(){
    var x = 5
    if (true){
        let x= 10
        console.log(x)
    }
    console.log(x)
}
 
//exemplo_var
exemplo_let
 
console.log("olá");console.log("fatec")


<html>

<head>

	<script>
		var saldo = 100.5; //Float
		var nome = prompt("Qual o seu nome?: ");
		alert("Olá," + nome, "é um prazer ter você por aqui!");
		function inicio() {

			

			var escolha = parseInt(prompt('Selecione uma opção 1.) Saldo 2.) Depósito 3.) Saque 4.) Sair'));

			switch (escolha) {
				case 1:
					ver_saldo();
					break;
				case 2:
					fazer_deposito();
					break;
				case 3:
					fazer_saque();
					break;
				case 4:
					sair();
					break;
				default:
					erro();
					break;
			}
		}		

		function ver_saldo() {
			alert('Seu saldo atual é: ' + saldo);
			inicio();
		}

		function fazer_deposito() {
			var deposito = parseFloat(prompt('Qual o valor para depósito?'));
			
			if (isNaN(deposito) || deposito === '') {
				alert('Por favor, informe um número:');
				fazer_deposito();
			} else {
				saldo += deposito;
				ver_saldo();
			}
		}

		function fazer_saque() {
			var saque = parseFloat(prompt('Qual o valor para saque?'));
			if (isNaN(saque) || saque === '') {
				alert('Por favor, informe um número:');
				fazer_saque();
			} else if (saque > saldo) {
				alert("Operação não autorizada");
				fazer_saque();
			} else {
				saldo -= saque;
				
				ver_saldo();
			}
		}
		
	
		function erro() {
			alert('Por favor, informe um número entre 1 e 4');
			inicio();
		}

		function sair() {
			var confirma = confirm('Você deseja sair?');
			if (confirma) {
				window.close();
			} else {
				inicio();
			}
		}

		function extrato() {
			alert("Suas últimas compras foram: 7,90 - Coxinha")
		}
		
		inicio();
	</script>
</head>

<body>

</body>

</html>
