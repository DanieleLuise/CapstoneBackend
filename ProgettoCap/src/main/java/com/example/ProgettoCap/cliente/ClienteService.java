package com.example.ProgettoCap.cliente;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ClienteService {

    @Autowired ClienteRepository clienteRepository;



    //GET ALL
    public List<ClienteResponsePrj> finAll(){
        return clienteRepository.findAllBy();
    }


    //GET X ID
    public Response findById(Long id){
        if (!clienteRepository.existsById(id)){
            throw new EntityNotFoundException("Cliente non trovato");
        }
        Cliente entity = clienteRepository.findById(id).get();
        Response response = new Response();
        BeanUtils.copyProperties(entity,response);
        return response;
    }

      //POST
    @Transactional
    public Response create(@Valid Request request){
        if (clienteRepository.existsByCodiceFiscaleAndNomeAndCognome(request.getCodiceFiscale(),request.getNome(),request.getCognome())){
            throw new EntityExistsException("il cliente esiste già");
        }
        Cliente entity = new Cliente();
        BeanUtils.copyProperties(request, entity);
        Cliente savedEntity = clienteRepository.save(entity);
        Response response = new Response();
        BeanUtils.copyProperties(savedEntity, response);
        return response;
    }

    //PUT
    public Response modify(Long id, @Valid Request request){
        if (!clienteRepository.existsById(id)){
            throw new EntityNotFoundException("cliente non trovato");
        }
        Cliente entity = clienteRepository.findById(id).get();
        BeanUtils.copyProperties(request, entity);
        clienteRepository.save(entity);
        Response response = new Response();
        BeanUtils.copyProperties(entity,response);
        return response;
    }

    //Delete
    public String delete(Long id){
        if (!clienteRepository.existsById(id)){
            throw new EntityNotFoundException();
        }
        clienteRepository.deleteById(id);
        return "cliente eliminato";

    }



}
