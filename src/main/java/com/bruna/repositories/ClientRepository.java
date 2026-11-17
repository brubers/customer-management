package com.bruna.repositories;

import com.bruna.entities.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.List;

public class ClientRepository {
    private final EntityManagerFactory emf; //fabrica de entitymanager

    public ClientRepository(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public void save(Client client) {
        try (EntityManager em = emf.createEntityManager()) { //caso persist lance uma excecao
            EntityTransaction et = em.getTransaction(); //abstraindo o entity transaction pra ficar mais facil de lidar com excecoes

            try {

                //iniciando transicao
                et.begin();
                //tornando o objeto gerenciado
                em.persist(client);
                //committando
                et.commit();

            } catch (RuntimeException e) {
                if (et.isActive()) {
                    et.rollback(); //desfaz as mudanças não confirmadas
                }
                throw e;//joga o erro
            }
        }
    }

    public Client findById(Long id) {
        EntityManager em = emf.createEntityManager();
        Client client = em.find(Client.class, id);
        em.close();
        return client;

    }

    public List<Client> findAll() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Client> query = em.createQuery("SELECT c FROM Client c", Client.class);
        List<Client> clients = query.getResultList();
        em.close();
        return clients;
    }

    public void deleteById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction et = em.getTransaction(); //abstraindo o entity transaction pra ficar mais facil de lidar com excecoes

            try {

                et.begin();
                Client client = em.find(Client.class, id);
                if (client != null) {
                    em.remove(client); //a entidade deve estar no estado managed
                }
                et.commit();

            } catch (RuntimeException e) {
                if (et.isActive()) {
                    et.rollback();
                }
                throw e;
            }
        }
    }

    public void update(Long id, String name, String email, String phone) {
        try (EntityManager em = emf.createEntityManager()) {
            EntityTransaction et = em.getTransaction(); //abstraindo o entity transaction pra ficar mais facil de lidar com excecoes

            try {
                et.begin();
                Client client = em.find(Client.class, id); //recupera no estado managed
                if (client != null) {
                    client.setName(name);
                    client.setEmail(email);
                    client.setPhone(phone);
                }
                et.commit();
            } catch (RuntimeException e) {
                if (et.isActive()) {
                    et.rollback();
                }
                throw e;
            }
        }
    }
}

