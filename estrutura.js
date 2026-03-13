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