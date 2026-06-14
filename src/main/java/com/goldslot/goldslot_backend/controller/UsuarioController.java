package com.goldslot.goldslot_backend.controller;

import com.goldslot.goldslot_backend.dto.UsuarioDTO;
import com.goldslot.goldslot_backend.entity.Usuario;
import com.goldslot.goldslot_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setPassword("encrypted_password"); // TODO: Implementar encriptación
        usuario.setNombreEscuela(usuarioDTO.getNombreEscuela());
        usuario.setPlan(usuarioDTO.getPlan());

        Usuario creado = usuarioService.crearUsuario(usuario);
        return ResponseEntity.ok(convertToDTO(creado));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerPorId(@PathVariable Long id) {
        return usuarioService.obtenerPorId(id)
                .map(u -> ResponseEntity.ok(convertToDTO(u)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> obtenerTodos() {
        List<UsuarioDTO> usuarios = usuarioService.obtenerTodos()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(usuarios);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuarioDetails = new Usuario();
        usuarioDetails.setNombreEscuela(usuarioDTO.getNombreEscuela());
        usuarioDetails.setPlan(usuarioDTO.getPlan());

        Usuario actualizado = usuarioService.actualizar(id, usuarioDetails);
        return ResponseEntity.ok(convertToDTO(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    private UsuarioDTO convertToDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getEmail(), usuario.getNombreEscuela(), usuario.getPlan());
    }
}