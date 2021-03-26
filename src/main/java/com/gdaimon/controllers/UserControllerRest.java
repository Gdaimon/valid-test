package com.gdaimon.controllers;

import com.gdaimon.models.User;
import com.gdaimon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserControllerRest {

    @Autowired
    private UserService userService;

    @RequestMapping(value = {"/",}, method = RequestMethod.GET)
    public Map<String, String> inicio() {
        Map<String, String> menu = new HashMap<>();
        menu.put("app", "http://localhost:8080/");
        menu.put("principal", "http://localhost:8080/api/");
        menu.put("listar", "http://localhost:8080/api/listar-usuarios");
        menu.put("crear", "http://localhost:8080/api/crear");
        menu.put("actualizar", "http://localhost:8080/api/actualizar");
        return menu;
    }


    @RequestMapping(value = {"/crear"}, method = RequestMethod.POST)
    public User crearUsuario(@RequestBody User usuario) {
        usuario.setProcesado(false);
        return userService.save(usuario);
    }

    @GetMapping(value = "/listar-usuarios")
    public List<User> listarUsuarios() {
        return userService.findAll();
    }

    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public List<User> actualizarUsuarios(@RequestBody List<User> listaUsuarios) {
        for (User usuario : listaUsuarios) {
            Optional<User> optionalUser = userService.findById(usuario.getId());
            if (optionalUser.isPresent()) {
                User userUpdate = optionalUser.get();
                userUpdate.setProcesado(usuario.getProcesado());
                userService.save(userUpdate);
            }
        }
        return userService.findAll();
    }
}
