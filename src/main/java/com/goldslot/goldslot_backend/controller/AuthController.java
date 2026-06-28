package com.goldslot.goldslot_backend.controller;

import com.goldslot.goldslot_backend.entity.Usuario;
import com.goldslot.goldslot_backend.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");

        Usuario usuario = usuarioService.obtenerPorEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos"));

        if (!usuario.getPassword().equals(password)) {
            throw new RuntimeException("Usuario o contraseña incorrectos");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("id", usuario.getId());
        response.put("email", usuario.getEmail());
        response.put("nombreEscuela", usuario.getNombreEscuela());
        response.put("token", "token-" + usuario.getId());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String password = request.get("password");
        String nombreEscuela = request.get("nombreEscuela");

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setNombreEscuela(nombreEscuela);
        usuario.setPlan("básico");

        Usuario creado = usuarioService.crearUsuario(usuario);

        Map<String, Object> response = new HashMap<>();
        response.put("id", creado.getId());
        response.put("email", creado.getEmail());
        response.put("nombreEscuela", creado.getNombreEscuela());
        response.put("token", "token-" + creado.getId());

        return ResponseEntity.ok(response);
    }
}