package com.interview.libraryapi.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.libraryapi.data.dto.v1.LivroDTO;
import com.interview.libraryapi.model.Livro;
import com.interview.libraryapi.repositories.LivroRepository;
import com.interview.libraryapi.service.BookApiService;
import com.interview.libraryapi.util.PropertiesReader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class BookApiServiceImpl implements BookApiService {

    @Autowired
    LivroRepository repository;

    public JsonNode procurarLivrosBookApi(String titulo) throws JsonProcessingException {
        String json ="";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(PropertiesReader.getProperty("API_URL") + "?q=" + titulo + "&key=" + PropertiesReader.getProperty("API_KEY"));
        Request request = builder.build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            json = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(json);
        ObjectMapper mapper = new ObjectMapper();

        try {
            JsonNode node = mapper.readTree(json);
            return node;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public LivroDTO salvarLivroApiPorId(String id) throws JsonProcessingException{
        String json ="";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder builder = new Request.Builder();
        builder.url(PropertiesReader.getProperty("API_URL") + id + "&key=" + PropertiesReader.getProperty("API_KEY"));
        Request request = builder.build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            json = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(json);
        ObjectMapper mapper = new ObjectMapper();

        Livro livro = new Livro();
        try {
            JsonNode node = mapper.readTree(json);
            livro.setTitulo(node.get("items").get(0).get("volumeInfo").get("title").asText());
            livro.setAutor(node.get("items").get(0).get("volumeInfo").get("authors").get(0).asText());
            DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date date = formatter.parse(node.get("items").get(0).get("volumeInfo").get("publishedDate").textValue());
            livro.setDataPublicacao(date);
            livro.setIsbn(node.get("items").get(0).get("volumeInfo").get("industryIdentifiers").get(0).get("identifier").textValue());
            livro.setCategoria(node.get("items").get(0).get("volumeInfo").get("categories").get(0).asText());
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Livro livroSalvo = repository.save(livro);
        return new LivroDTO(livroSalvo);
    }
}
