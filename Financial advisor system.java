package com.example.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.util.ArrayList;
import java.util.List;

@Entity
public class FinancialAdvisor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "advisor", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Client> clients = new ArrayList<>();

    // JPA no-arg constructor
    public FinancialAdvisor() {}

    // Constructor initializing all instance variables (except id which is auto-generated)
    public FinancialAdvisor(String name, String email, List<Client> clients) {
        this.name = name;
        this.email = email;
        this.clients = clients == null ? new ArrayList<>() : clients;
        // ensure bidirectional consistency
        for (Client c : this.clients) {
            c.setAdvisor(this);
        }
    }

    // getters (no setter for id)
    public Long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public List<Client> getClients() { return clients; }
    public void setClients(List<Client> clients) {
        this.clients = clients == null ? new ArrayList<>() : clients;
        for (Client c : this.clients) {
            c.setAdvisor(this);
        }
    }

    // convenience helpers
    public void addClient(Client client) {
        clients.add(client);
        client.setAdvisor(this);
    }

    public void removeClient(Client client) {
        clients.remove(client);
        client.setAdvisor(null);
    }
}
