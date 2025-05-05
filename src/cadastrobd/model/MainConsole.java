package cadastrobd.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MainConsole {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PessoaFisicaDAO pfDAO = new PessoaFisicaDAO();
        PessoaJuridicaDAO pjDAO = new PessoaJuridicaDAO();
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1 - Incluir");
            System.out.println("2 - Alterar");
            System.out.println("3 - Excluir");
            System.out.println("4 - Exibir por ID");
            System.out.println("5 - Exibir todos");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");


            int opc = Integer.parseInt(sc.nextLine());
            try {
                switch (opc) {
                    case 1:
                        System.out.print("Pessoa Física (F) ou Jurídica (J)? ");
                        String tipoInc = sc.nextLine().trim().toUpperCase();
                        if (tipoInc.equals("F")) {
                            System.out.print("Nome: ");
                            String nomeF = sc.nextLine();
                            System.out.print("Logradouro: ");
                            String logF = sc.nextLine();
                            System.out.print("Telefone: ");
                            String telF = sc.nextLine();
                            System.out.print("CPF: ");
                            String cpf = sc.nextLine();
                            PessoaFisica pf = new PessoaFisica(0, nomeF, logF, null, null, telF, null, cpf);
                            pfDAO.incluir(pf);
                            System.out.println("Incluída PF com ID=" + pf.getId());
                        } else {
                            System.out.print("Nome: ");
                            String nomeJ = sc.nextLine();
                            System.out.print("Logradouro: ");
                            String logJ = sc.nextLine();
                            System.out.print("Telefone: ");
                            String telJ = sc.nextLine();
                            System.out.print("CNPJ: ");
                            String cnpj = sc.nextLine();
                            PessoaJuridica pj = new PessoaJuridica(0, nomeJ, logJ, null, null, telJ, null, cnpj);
                            pjDAO.incluir(pj);
                            System.out.println("Incluída PJ com ID=" + pj.getId());
                        }
                        break;

                    case 2:
                        System.out.print("Pessoa Física (F) ou Jurídica (J)? ");
                        String tipoAlt = sc.nextLine().trim().toUpperCase();
                        System.out.print("ID: ");
                        int idAlt = Integer.parseInt(sc.nextLine());
                        if (tipoAlt.equals("F")) {
                            PessoaFisica existingF = pfDAO.getPessoa(idAlt);
                            if (existingF != null) {
                                System.out.print("Novo nome: ");
                                existingF.setNome(sc.nextLine());
                                System.out.print("Novo logradouro: ");
                                existingF.setLogradouro(sc.nextLine());
                                System.out.print("Novo telefone: ");
                                existingF.setTelefone(sc.nextLine());
                                System.out.print("Novo CPF: ");
                                existingF.setCpf(sc.nextLine());
                                pfDAO.alterar(existingF);
                                System.out.println("Alterada PF ID=" + idAlt);
                            } else {
                                System.out.println("PF não encontrada.");
                            }
                        } else {
                            PessoaJuridica existingJ = pjDAO.getPessoa(idAlt);
                            if (existingJ != null) {
                                System.out.print("Novo nome: ");
                                existingJ.setNome(sc.nextLine());
                                System.out.print("Novo logradouro: ");
                                existingJ.setLogradouro(sc.nextLine());
                                System.out.print("Novo telefone: ");
                                existingJ.setTelefone(sc.nextLine());
                                System.out.print("Novo CNPJ: ");
                                existingJ.setCnpj(sc.nextLine());
                                pjDAO.alterar(existingJ);
                                System.out.println("Alterada PJ ID=" + idAlt);
                            } else {
                                System.out.println("PJ não encontrada.");
                            }
                        }
                        break;

                    case 3:
                        System.out.print("Pessoa Física (F) ou Jurídica (J)? ");
                        String tipoExc = sc.nextLine().trim().toUpperCase();
                        System.out.print("ID: ");
                        int idExc = Integer.parseInt(sc.nextLine());
                        if (tipoExc.equals("F")) {
                            pfDAO.excluir(idExc);
                            System.out.println("Excluída PF ID=" + idExc);
                        } else {
                            pjDAO.excluir(idExc);
                            System.out.println("Excluída PJ ID=" + idExc);
                        }
                        break;

                    case 4:
                        System.out.print("Pessoa Física (F) ou Jurídica (J)? ");
                        String tipoExb = sc.nextLine().trim().toUpperCase();
                        System.out.print("ID: ");
                        int idExb = Integer.parseInt(sc.nextLine());
                        if (tipoExb.equals("F")) {
                            PessoaFisica pfRes = pfDAO.getPessoa(idExb);
                            if (pfRes != null) pfRes.exibir();
                            else System.out.println("PF não encontrada.");
                        } else {
                            PessoaJuridica pjRes = pjDAO.getPessoa(idExb);
                            if (pjRes != null) pjRes.exibir();
                            else System.out.println("PJ não encontrada.");
                        }
                        break;

                    case 5:
                        System.out.print("Listar Todos — PF ou PJ? ");
                        String tipoAll = sc.nextLine().trim().toUpperCase();
                        if (tipoAll.equals("F")) {
                            List<PessoaFisica> allF = pfDAO.getPessoas();
                            allF.forEach(Pessoa::exibir);
                        } else {
                            List<PessoaJuridica> allJ = pjDAO.getPessoas();
                            allJ.forEach(Pessoa::exibir);
                        }
                        break;

                    case 0:
                        running = false;
                        break;

                    default:
                        System.out.println("Opção inválida.");
                }
            } catch (SQLException e) {
                System.err.println("Erro: " + e.getMessage());
            }
        }

        sc.close();
        System.out.println("Programa encerrado.");
    }
}
