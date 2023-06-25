package com.example.vic.Client;

import java.util.List;

public interface ClientService {
    boolean saveClient(Client client);
    List<Client> getClient();
}
