
package com.bruna;

import com.bruna.entities.Client;
import com.bruna.repositories.ClientRepository;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;
import java.util.Scanner;

//main feita por ia, porém manualmente avaliada;
public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("customer-management");

        ClientRepository repository = new ClientRepository(emf);

        Scanner sc = new Scanner(System.in);

        int option;
        //do while -> faz primeiro e dps avalia loop
        do {
            System.out.println("\n===== CUSTOMER MANAGEMENT =====");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Buscar cliente por ID");
            System.out.println("3 - Listar todos os clientes");
            System.out.println("4 - Atualizar cliente");
            System.out.println("5 - Excluir cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            option = sc.nextInt();
            sc.nextLine();

            switch (option) {

                case 1:
                    System.out.println("\n--- Cadastro de cliente ---");

                    System.out.print("Nome: ");
                    String name = sc.nextLine();

                    System.out.print("Email: ");
                    String email = sc.nextLine();

                    System.out.print("Telefone: ");
                    String phone = sc.nextLine();

                    Client newClient = new Client(phone, name, email);

                    repository.save(newClient);

                    System.out.println("Cliente cadastrado com sucesso!");
                    System.out.println("ID gerado: " + newClient.getId());
                    break;

                case 2:
                    System.out.println("\n--- Buscar cliente ---");

                    System.out.print("Informe o ID: ");
                    Long searchId = sc.nextLong();

                    Client client = repository.findById(searchId);

                    if (client != null) {
                        System.out.println("\nCliente encontrado:");
                        System.out.println("ID: " + client.getId());
                        System.out.println("Nome: " + client.getName());
                        System.out.println("Email: " + client.getEmail());
                        System.out.println("Telefone: " + client.getPhone());
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Lista de clientes ---");

                    List<Client> clients = repository.findAll();

                    if (clients.isEmpty()) {
                        System.out.println("Nenhum cliente cadastrado.");
                    } else {
                        for (Client c : clients) {
                            System.out.println("------------------------");
                            System.out.println("ID: " + c.getId());
                            System.out.println("Nome: " + c.getName());
                            System.out.println("Email: " + c.getEmail());
                            System.out.println("Telefone: " + c.getPhone());
                        }
                    }
                    break;

                case 4:
                    System.out.println("\n--- Atualizar cliente ---");

                    System.out.print("Informe o ID: ");
                    Long updateId = sc.nextLong();
                    sc.nextLine();

                    Client existingClient = repository.findById(updateId);

                    if (existingClient != null) {

                        System.out.print("Novo nome: ");
                        String newName = sc.nextLine();

                        System.out.print("Novo email: ");
                        String newEmail = sc.nextLine();

                        System.out.print("Novo telefone: ");
                        String newPhone = sc.nextLine();

                        repository.update(
                                updateId,
                                newName,
                                newEmail,
                                newPhone
                        );

                        System.out.println("Cliente atualizado com sucesso!");

                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- Excluir cliente ---");

                    System.out.print("Informe o ID: ");
                    Long deleteId = sc.nextLong();

                    Client clientToDelete = repository.findById(deleteId);

                    if (clientToDelete != null) {
                        repository.deleteById(deleteId);
                        System.out.println("Cliente excluído com sucesso!");
                    } else {
                        System.out.println("Cliente não encontrado.");
                    }
                    break;

                case 0:
                    System.out.println("Encerrando aplicação...");
                    break;

                default:
                    System.out.println("Opção inválida!");
                    break;
            }

        } while (option != 0);

        sc.close();
        emf.close();
    }
}
