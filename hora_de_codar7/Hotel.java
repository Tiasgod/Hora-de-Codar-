import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Hotel {

    static final String NOME_HOTEL = "Hotel do TiTi";
    static final String SENHA = "2678";
    static final int TOTAL_QUARTOS = 20;

    static Scanner sc = new Scanner(System.in);
    static String nomeUsuario = "";


    static boolean[] quartos = new boolean[TOTAL_QUARTOS + 1]; // true = ocupado
    static ArrayList<String[]> reservas = new ArrayList<>();     // [nome, quarto, tipo, diarias, total]
    static ArrayList<String[]> hospedes = new ArrayList<>();     // [nome, datahora]
    static int totalEventos = 0;
    static double receitaHospedagem = 0;
    static double receitaEventos = 0;


    static void autenticar() {
        System.out.println("=============================");
        System.out.println("  Bem-vindo ao Hotel " + NOME_HOTEL);
        System.out.println("=============================");
        System.out.print("Nome do usuário: ");
        nomeUsuario = sc.nextLine().trim();

        for (int tentativas = 1; tentativas <= 3; tentativas++) {
            System.out.print("Senha: ");
            String senha = sc.nextLine().trim();
            if (senha.equals(SENHA)) {
                System.out.println("Bem-vindo ao Hotel " + NOME_HOTEL + ", " + nomeUsuario + ". É um imenso prazer ter você por aqui!\n");
                return;
            }
            System.out.println("Senha incorreta. Tentativa " + tentativas + "/3.");
        }
        System.out.println("Sistema bloqueado por excesso de tentativas. Até logo!");
        System.exit(0);
    }

    // Menu Principal

    static void menu() {
        while (true) {
            System.out.println("\n Menu Principal ");
            System.out.println("1. Reservas de Quartos");
            System.out.println("2. Cadastro de Hóspedes");
            System.out.println("3. Eventos");
            System.out.println("4. Ar-Condicionado");
            System.out.println("5. Abastecimento");
            System.out.println("6. Relatórios Operacionais");
            System.out.println("7. Sair");
            System.out.print("Opção: ");

            String entrada = sc.nextLine().trim();
            switch (entrada) {
                case "1": reservas(); break;
                case "2": hospedes(); break;
                case "3": eventos(); break;
                case "4": arCondicionado(); break;
                case "5": abastecimento(); break;
                case "6": relatorios(); break;
                case "7":
                    System.out.println("Muito obrigado e até logo, " + nomeUsuario + ".");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Informe um número entre 1 e 7.");
            }
        }
    }

    // Subprograma 1

    static void reservas() {
        System.out.println("\n[Reservas de Quartos]");

        System.out.print("Informe o valor da diária: R$ ");
        double diaria;
        try { diaria = Double.parseDouble(sc.nextLine().trim()); }
        catch (Exception e) { System.out.println("Valor inválido, " + nomeUsuario + "."); return; }

        System.out.print("Informe a quantidade de diárias (1-30): ");
        int diasNum;
        try { diasNum = Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { System.out.println("Valor inválido, " + nomeUsuario + "."); return; }

        if (diaria <= 0 || diasNum < 1 || diasNum > 30) {
            System.out.println("Valor inválido, " + nomeUsuario + ".");
            return;
        }

        System.out.print("Informe o nome do hóspede: ");
        String nomeHospede = sc.nextLine().trim();

        System.out.print("Tipo de quarto (S=Standard / E=Executivo / L=Luxo): ");
        String tipo = sc.nextLine().trim().toUpperCase();
        double fator;
        String tipoNome;
        switch (tipo) {
            case "E": fator = 1.35; tipoNome = "Executivo"; break;
            case "L": fator = 1.65; tipoNome = "Luxo"; break;
            default:  fator = 1.00; tipoNome = "Standard"; tipo = "S"; break;
        }

        // Escolher quarto
        int numQuarto = escolherQuarto();
        if (numQuarto == -1) return;

        // Calcular valores
        double subtotal = diaria * diasNum * fator;
        double taxa = subtotal * 0.10;
        double total = subtotal + taxa;

        System.out.println("\n--- Resumo ---");
        System.out.println("Hóspede : " + nomeHospede);
        System.out.println("Quarto  : " + numQuarto + " (" + tipoNome + ")");
        System.out.printf("Subtotal: R$ %.2f%n", subtotal);
        System.out.printf("Taxa 10%%: R$ %.2f%n", taxa);
        System.out.printf("Total   : R$ %.2f%n", total);

        System.out.print(nomeUsuario + ", confirma a reserva? (S/N): ");
        String confirma = sc.nextLine().trim().toUpperCase();
        if (confirma.equals("S")) {
            quartos[numQuarto] = true;
            reservas.add(new String[]{nomeHospede, String.valueOf(numQuarto), tipoNome, String.valueOf(diasNum), String.format("%.2f", total)});
            receitaHospedagem += total;
            System.out.println("Reserva efetuada com sucesso.");
            exibirMapaQuartos();
        } else {
            System.out.println("Reserva não efetuada.");
        }
    }

    static int escolherQuarto() {
        while (true) {
            System.out.print("Escolha um quarto (1-20): ");
            int q;
            try { q = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { System.out.println("Número inválido."); continue; }

            if (q < 1 || q > 20) { System.out.println("Quarto deve ser entre 1 e 20."); continue; }

            if (!quartos[q]) return q;

            System.out.println("Quarto " + q + " já está ocupado. Quartos livres:");
            for (int i = 1; i <= 20; i++) if (!quartos[i]) System.out.print(i + " ");
            System.out.println();

            System.out.print("Deseja escolher outro quarto? (S/N): ");
            if (!sc.nextLine().trim().toUpperCase().equals("S")) return -1;
        }
    }

    static void exibirMapaQuartos() {
        System.out.println("\nMapa de quartos:");
        for (int i = 1; i <= 20; i++) {
            System.out.print("[" + (quartos[i] ? "O" : "L") + "] ");
            if (i % 4 == 0) System.out.println();
        }
    }

    // Subprograma 2

    static void hospedes() {
        while (true) {
            System.out.println("\n[Cadastro de Hóspedes]");
            System.out.println("1-Cadastrar  2-Pesquisar exato  3-Pesquisar prefixo  4-Listar  5-Atualizar  6-Remover  7-Voltar");
            System.out.print("Opção: ");
            String op = sc.nextLine().trim();

            switch (op) {
                case "1": cadastrarHospede(); break;
                case "2": pesquisarExato(); break;
                case "3": pesquisarPrefixo(); break;
                case "4": listarHospedes(); break;
                case "5": atualizarHospede(); break;
                case "6": removerHospede(); break;
                case "7": return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    static void cadastrarHospede() {
        if (hospedes.size() >= 15) { System.out.println("Máximo de cadastros atingido."); return; }
        System.out.print("Nome do hóspede: ");
        String nome = sc.nextLine().trim();
        for (String[] h : hospedes) if (h[0].equalsIgnoreCase(nome)) { System.out.println("Hóspede já cadastrado."); return; }
        String agora = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        hospedes.add(new String[]{nome, agora});
        System.out.println("Hóspede cadastrado com sucesso.");
    }

    static void pesquisarExato() {
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        for (String[] h : hospedes) {
            if (h[0].equalsIgnoreCase(nome)) { System.out.println("Hóspede " + h[0] + " foi encontrado."); return; }
        }
        System.out.println("Hóspede não encontrado.");
    }

    static void pesquisarPrefixo() {
        System.out.print("Prefixo: ");
        String prefixo = sc.nextLine().trim().toLowerCase();
        boolean achou = false;
        int idx = 1;
        for (String[] h : hospedes) {
            if (h[0].toLowerCase().startsWith(prefixo)) { System.out.println("[" + idx + "] " + h[0]); achou = true; }
            idx++;
        }
        if (!achou) System.out.println("Nenhum hóspede encontrado.");
    }

    static void listarHospedes() {
        if (hospedes.isEmpty()) { System.out.println("Nenhum hóspede cadastrado."); return; }
        hospedes.sort((a, b) -> a[0].compareToIgnoreCase(b[0]));
        System.out.println("Lista de hóspedes:");
        for (int i = 0; i < hospedes.size(); i++)
            System.out.println("[" + (i + 1) + "] " + hospedes.get(i)[0] + " — " + hospedes.get(i)[1]);
    }

    static void atualizarHospede() {
        listarHospedes();
        if (hospedes.isEmpty()) return;
        System.out.print("Índice para atualizar: ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx < 0 || idx >= hospedes.size()) { System.out.println("Índice inválido."); return; }
            System.out.print("Novo nome: ");
            hospedes.get(idx)[0] = sc.nextLine().trim();
            System.out.println("Operação realizada com sucesso.");
        } catch (Exception e) { System.out.println("Entrada inválida."); }
    }

    static void removerHospede() {
        listarHospedes();
        if (hospedes.isEmpty()) return;
        System.out.print("Índice para remover: ");
        try {
            int idx = Integer.parseInt(sc.nextLine().trim()) - 1;
            if (idx < 0 || idx >= hospedes.size()) { System.out.println("Índice inválido."); return; }
            hospedes.remove(idx);
            System.out.println("Operação realizada com sucesso.");
        } catch (Exception e) { System.out.println("Entrada inválida."); }
    }

    // Subprograma 3
    static void eventos() {
        System.out.println("\n[Eventos]");

        // Parte A: Auditório
        System.out.print("Número de convidados: ");
        int convidados;
        try { convidados = Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { System.out.println("Número de convidados inválido."); return; }

        if (convidados < 0 || convidados > 350) { System.out.println("Número de convidados inválido."); return; }

        String auditorio;
        int cadeirasExtras = 0;
        if (convidados <= 220) {
            auditorio = "Laranja";
            if (convidados > 150) cadeirasExtras = convidados - 150;
            System.out.println("Auditório selecionado: Laranja" + (cadeirasExtras > 0 ? " (" + cadeirasExtras + " cadeiras adicionais)" : ""));
        } else {
            auditorio = "Colorado";
            System.out.println("Auditório selecionado: Colorado");
        }

        // Parte B: Agenda
        System.out.print("Dia da semana (ex: segunda, sabado): ");
        String dia = sc.nextLine().trim().toLowerCase();

        System.out.print("Hora inicial (7-23): ");
        int horaInicio;
        try { horaInicio = Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { System.out.println("Hora inválida."); return; }

        System.out.print("Duração (horas, 1-12): ");
        int duracao;
        try { duracao = Integer.parseInt(sc.nextLine().trim()); }
        catch (Exception e) { System.out.println("Duração inválida."); return; }

        if (duracao < 1 || duracao > 12) { System.out.println("Duração inválida."); return; }

        int horaFim = horaInicio + duracao;
        boolean fimDeSemana = dia.equals("sabado") || dia.equals("domingo");
        int limiteHora = fimDeSemana ? 15 : 23;

        if (horaInicio < 7 || horaFim > limiteHora) {
            System.out.println("Auditório indisponível nesse horário.");
            return;
        }

        System.out.print("Nome da empresa: ");
        String empresa = sc.nextLine().trim();
        System.out.println("Auditório reservado para " + empresa + ": " + dia + " às " + horaInicio + "h até " + horaFim + "h.");

        // Parte C: Garçons
        int garconsBase = (int) Math.ceil((double) convidados / 12);
        int garconsDuracao = duracao / 2;
        int totalGarcons = garconsBase + garconsDuracao;
        double custoGarcons = totalGarcons * duracao * 10.50;

        System.out.println("Garçons necessários: " + totalGarcons);
        System.out.printf("Custo com garçons: R$ %.2f%n", custoGarcons);

        // Parte D: Buffet
        double cafe = convidados * 0.2;
        double agua = convidados * 0.5;
        int salgados = convidados * 7;
        double custoCafe = cafe * 0.80;
        double custoAgua = agua * 0.40;
        double custoSalgados = (salgados / 100.0) * 34.00;
        double custoBuffet = custoCafe + custoAgua + custoSalgados;

        System.out.println("\nBuffet:");
        System.out.printf("Café    : %.1f L%n", cafe);
        System.out.printf("Água    : %.1f L%n", agua);
        System.out.println("Salgados: " + salgados + " un");
        System.out.printf("Custo buffet: R$ %.2f%n", custoBuffet);

        // Parte E: Relatório
        double totalEvento = custoGarcons + custoBuffet;
        System.out.printf("Total do evento: R$ %.2f%n", totalEvento);

        System.out.print("Confirmar reserva? (S/N): ");
        String confirma = sc.nextLine().trim().toUpperCase();
        if (confirma.equals("S")) {
            totalEventos++;
            receitaEventos += totalEvento;
            System.out.println("Reserva efetuada com sucesso.");
        } else {
            System.out.println("Reserva não efetuada.");
        }
    }

    // Subprograma 4

    static void arCondicionado() {
        System.out.println("\n[Ar-Condicionado]");

        ArrayList<String[]> orcamentos = new ArrayList<>();

        while (true) {
            System.out.print("Empresa: ");
            String empresa = sc.nextLine().trim();

            System.out.print("Valor por aparelho: R$ ");
            double valorAparelho;
            try { valorAparelho = Double.parseDouble(sc.nextLine().trim()); }
            catch (Exception e) { System.out.println("Valor inválido."); continue; }

            System.out.print("Quantidade de aparelhos: ");
            int qtd;
            try { qtd = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { System.out.println("Valor inválido."); continue; }

            System.out.print("Desconto (%): ");
            double desconto;
            try { desconto = Double.parseDouble(sc.nextLine().trim()); }
            catch (Exception e) { desconto = 0; }

            System.out.print("Mínimo de aparelhos para desconto: ");
            int minimo;
            try { minimo = Integer.parseInt(sc.nextLine().trim()); }
            catch (Exception e) { minimo = Integer.MAX_VALUE; }

            System.out.print("Valor de deslocamento: R$ ");
            double deslocamento;
            try { deslocamento = Double.parseDouble(sc.nextLine().trim()); }
            catch (Exception e) { deslocamento = 0; }

            double bruto = valorAparelho * qtd;
            double valorDesconto = (qtd >= minimo) ? bruto * (desconto / 100) : 0;
            double total = bruto - valorDesconto + deslocamento;

            System.out.printf("O serviço de %s custará R$ %.2f%n", empresa, total);
            orcamentos.add(new String[]{empresa, String.format("%.2f", total)});

            System.out.print("Deseja informar novos dados, " + nomeUsuario + "? (S/N): ");
            if (!sc.nextLine().trim().toUpperCase().equals("S")) break;
        }

        if (orcamentos.size() < 2) { System.out.println("Informe pelo menos 2 empresas para comparação."); return; }

        String melhorEmpresa = orcamentos.get(0)[0];
        double melhorValor = Double.parseDouble(orcamentos.get(0)[1]);
        String piorEmpresa = orcamentos.get(0)[0];
        double piorValor = melhorValor;

        for (String[] o : orcamentos) {
            double v = Double.parseDouble(o[1]);
            if (v < melhorValor) { melhorValor = v; melhorEmpresa = o[0]; }
            if (v > piorValor)   { piorValor = v;   piorEmpresa = o[0]; }
        }

        double difPerc = ((piorValor - melhorValor) / melhorValor) * 100;
        System.out.printf("Melhor orçamento: %s — R$ %.2f%n", melhorEmpresa, melhorValor);
        System.out.printf("Pior  orçamento : %s — R$ %.2f%n", piorEmpresa, piorValor);
        System.out.printf("Diferença: %.1f%%%n", difPerc);
    }

    // Subprograma 5

    static void abastecimento() {
        System.out.println("\n[Abastecimento]");
        double tanque = 42.0;

        System.out.print("Wayne Oil  → Álcool: R$ ");
        double wAlcool = lerDouble();
        System.out.print("Wayne Oil  → Gasolina: R$ ");
        double wGasolina = lerDouble();

        System.out.print("Stark Petrol → Álcool: R$ ");
        double sAlcool = lerDouble();
        System.out.print("Stark Petrol → Gasolina: R$ ");
        double sGasolina = lerDouble();

        String wCombustivel = (wAlcool <= 0.70 * wGasolina) ? "Álcool" : "Gasolina";
        double wTotal = (wCombustivel.equals("Álcool") ? wAlcool : wGasolina) * tanque;

        String sCombustivel = (sAlcool <= 0.70 * sGasolina) ? "Álcool" : "Gasolina";
        double sTotal = (sCombustivel.equals("Álcool") ? sAlcool : sGasolina) * tanque;

        System.out.printf("Wayne Oil  : melhor opção = %s | Total (42L) = R$ %.2f%n", wCombustivel, wTotal);
        System.out.printf("Stark Petrol: melhor opção = %s | Total (42L) = R$ %.2f%n", sCombustivel, sTotal);

        if (wTotal <= sTotal) {
            System.out.printf("%s, é mais barato abastecer com %s no posto Wayne Oil.%n", nomeUsuario, wCombustivel);
        } else {
            System.out.printf("%s, é mais barato abastecer com %s no posto Stark Petrol.%n", nomeUsuario, sCombustivel);
        }
    }

    static double lerDouble() {
        try { return Double.parseDouble(sc.nextLine().trim()); }
        catch (Exception e) { return 0; }
    }

    // Subprograma 6

    static void relatorios() {
        System.out.println("\n[Relatórios Operacionais]");
        int ocupados = 0;
        for (int i = 1; i <= 20; i++) if (quartos[i]) ocupados++;

        System.out.println("-----------------------------------");
        System.out.printf("%-28s %s%n", "Reservas confirmadas:", reservas.size());
        System.out.printf("%-28s %d/%d (%.0f%%)%n", "Taxa de ocupação:", ocupados, 20, (ocupados / 20.0) * 100);
        System.out.printf("%-28s %s%n", "Hóspedes cadastrados:", hospedes.size());
        System.out.printf("%-28s %s%n", "Eventos confirmados:", totalEventos);
        System.out.println("-----------------------------------");
        System.out.printf("%-28s R$ %.2f%n", "Receita hospedagem:", receitaHospedagem);
        System.out.printf("%-28s R$ %.2f%n", "Receita eventos:", receitaEventos);
        System.out.printf("%-28s R$ %.2f%n", "Total geral:", receitaHospedagem + receitaEventos);
        System.out.println("-----------------------------------");
    }

    // main

    public static void main(String[] args) {
        autenticar();
        menu();
    }
}
