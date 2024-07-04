package com.example.ProgettoCap.prodotto;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.ProgettoCap.user.User;
import com.example.ProgettoCap.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;





@Service
public class ProdottoService {

    @Autowired
    ProdottoRepository prodottoRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private Cloudinary cloudinary;





    //GET
    public List<ProdottoResponsePrj> findAll(){
        return prodottoRepository.findAllBy();
    }

    public ProdottoResponse findById(Long id) {
        Prodotto prodotto = prodottoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato con ID: " + id));
        ProdottoResponse response = new ProdottoResponse();
        BeanUtils.copyProperties(prodotto, response);
        return response;
    }

    //POST
    @Transactional
    public ProdottoResponse create(ProdottoRequest request, MultipartFile[] files) throws IOException {
        // Verifica se l'ID dello user esiste nel database
        User user = userRepository.findById(request.getIdUser())
                .orElseThrow(() -> new EntityNotFoundException("Venditore non trovato con ID: " + request.getIdUser()));

        // Caricamento delle immagini su Cloudinary
        List<String> imageUrls = new ArrayList<>();
        for (MultipartFile file : files) {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            imageUrls.add(imageUrl);
        }

        // Creazione di un nuovo oggetto Prodotto
        Prodotto entity = new Prodotto();
        BeanUtils.copyProperties(request, entity);

        // Imposta l'URL delle immagini
        entity.setImmagine(String.join(",", imageUrls)); // Concatenazione degli URL in una singola stringa separata da virgole

        // Associazione dello user al prodotto
        entity.setUser(user);

        // Salvataggio del prodotto nel database
        Prodotto savedEntity = prodottoRepository.save(entity);

        // Creazione della risposta da restituire
        ProdottoResponse prodottoResponse = new ProdottoResponse();
        BeanUtils.copyProperties(savedEntity, prodottoResponse);
        prodottoResponse.setUser(user);
        return prodottoResponse;
    }


    @Transactional
    public ProdottoResponse updateProductQuantity(Long id, int newQuantity) {
        Prodotto prodotto = prodottoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato con ID: " + id));
        prodotto.setQuantita(newQuantity);
        prodotto.setAvailable(newQuantity > 0);
        Prodotto updatedProdotto = prodottoRepository.save(prodotto);
        ProdottoResponse response = new ProdottoResponse();
        BeanUtils.copyProperties(updatedProdotto, response);
        response.setUser(updatedProdotto.getUser());
        return response;

    }


    //PUT
    public ProdottoResponse modify(Long id, ProdottoRequest prodottoRequest){
        if (!userRepository.existsById(prodottoRequest.getIdUser())){
            throw new EntityNotFoundException("venditore non trovato");
        }
        Prodotto entity = prodottoRepository.findById(id).get();
        User user = userRepository.findById(prodottoRequest.getIdUser()).get();
        BeanUtils.copyProperties(prodottoRequest, entity);
        entity.setUser(user);
        prodottoRepository.save(entity);
        ProdottoResponse prodottoResponse = new ProdottoResponse();
        BeanUtils.copyProperties(entity, prodottoResponse);
        return prodottoResponse;

    }

   //DELETE
    public String delete(Long id){
        if (!prodottoRepository.existsById(id)){
            throw new EntityNotFoundException("prodotto non trovato");
        }
        prodottoRepository.deleteById(id);
        return "prodotto eliminato";
    }




}
