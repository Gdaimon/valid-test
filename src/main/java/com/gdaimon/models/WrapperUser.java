package com.gdaimon.models;

import java.util.ArrayList;
import java.util.List;

public class WrapperUser {
    private List<User> listaUsuarios = new ArrayList<>();

    public List<User> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<User> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
}
