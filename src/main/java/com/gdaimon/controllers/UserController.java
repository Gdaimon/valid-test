package com.gdaimon.controllers;

import com.gdaimon.models.User;
import com.gdaimon.models.WrapperUser;
import com.gdaimon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Metodo encargado de cargar el formulario inicial del registro de usuarios
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/", "index"}, method = RequestMethod.GET)
    public String inicio(Model model) {
        User usuario = new User();
        usuario.setProcesado(false);
        model.addAttribute("titulo", "Registro Usuarios");
        model.addAttribute("usuario", usuario);
        return "inicio";
    }

    /**
     * Metodo encargado de crear un usuario
     *
     * @param usuario
     * @param result
     * @return
     */
    @RequestMapping(value = {"/crear"}, method = RequestMethod.POST)
    public String crearUsuario(@Valid @ModelAttribute("usuario") User usuario, BindingResult result) {
        if (result.hasErrors()) {
            return "inicio";
        }
        userService.save(usuario);
        return "redirect:/listar-usuarios";
    }

    /**
     * Metodo encargado de cargar y listar todos los usuarios
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/listar-usuarios", method = RequestMethod.GET)
    public String listarUsuarios(Model model) {
        model.addAttribute("titulo", "Lista de Usuarios");
        List<User> userList = userService.findAll();
        model.addAttribute("usuarios", userList);
        model.addAttribute("listaUsuarios", new WrapperUser());
        return "lista-usuarios";
    }

    /**
     * Metodo encagado de actualizar los estados procesados de cada usuario
     *
     * @param listaUsuarios
     * @return
     */
    @RequestMapping(value = "/actualizar", method = RequestMethod.POST)
    public String actualizarUsuarios(@ModelAttribute WrapperUser listaUsuarios) {
        for (User usuario : listaUsuarios.getListaUsuarios()) {
            Optional<User> optionalUser = userService.findById(usuario.getId());
            if (optionalUser.isPresent()) {
                User userUpdate = optionalUser.get();
                userUpdate.setProcesado(usuario.getProcesado());
                userService.save(userUpdate);
            }
        }
        return "redirect:/listar-usuarios";
    }
}
