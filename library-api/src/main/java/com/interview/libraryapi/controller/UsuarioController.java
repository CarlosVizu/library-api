package com.interview.libraryapi.controller;

import com.interview.libraryapi.data.dto.v1.UsuarioDTO;
import com.interview.libraryapi.service.UsuarioService;
import com.interview.libraryapi.util.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario/v1")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping(value = "/test")
    public String teste (){
        return "olaaaa";
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON})
    public List<UsuarioDTO> procurarUsuariosPorNome(@Param(value = "nome") String nome) {
        return service.procurarUsuarioPorNome(nome);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON})
    public void salvarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        service.salvarUsuario(usuarioDTO);
    }

    @PutMapping(
            consumes = {MediaType.APPLICATION_JSON},
            produces = {MediaType.APPLICATION_JSON})
    public UsuarioDTO atualizarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return service.atualizarUsuario(usuarioDTO);
    }

    @DeleteMapping(consumes = {MediaType.APPLICATION_JSON})
    public void deletarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        service.deletarUsuario(usuarioDTO);
    }
}
