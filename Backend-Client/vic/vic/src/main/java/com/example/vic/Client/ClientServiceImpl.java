package com.example.vic.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClientServiceImpl implements ClientService {
    @Autowired

    ClientRepository clientRepository;

    @Override

    public boolean saveClient(Client client) {

        try {
            Client savedClient = clientRepository.save(client);
            if( savedClient != null) {
                return true;
            }else {
                return false ;
            } }
        catch (Exception e) {


        }
        return false;
    }
    @Override
    public List<Client> getClient(){
        return clientRepository.findAll();
    }
}
