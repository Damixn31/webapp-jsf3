package org.olmedo.webapp.jsf3.controllers;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ValueChangeEvent;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@Named
@SessionScoped /* tiene que ser de @SessionScoped porque si hacemos un switch y seleccionamos un
idioma en particular la idea es que se mantenga en los siguientes request, porque si lo dejamos
solamente del request y hacemos un click despues se pierde el lenguaje*/
public class LenguajeController implements Serializable {

    private static final long serialVersionUID = 187328738L;

    private Locale locale;
    private String lenguaje;
    private Map<String, String> lenguajesSoportados;

    @PostConstruct
    public void init() {
        locale = FacesContext.getCurrentInstance().getViewRoot().getLocale(); /* optenemos la localizacion actual
         despues se puede cambiar cuando hacemos switch entre uno y otro*/
        lenguajesSoportados = new HashMap<>();
        lenguajesSoportados.put("Ingles", "en");
        lenguajesSoportados.put("Espanol", "es");
    }

    /*metodo de evento*/
    public void seleccionar(ValueChangeEvent event) {
        String nuevo = event.getNewValue().toString();
        lenguajesSoportados.values().forEach(v -> {
            if (v.equals(nuevo)) {
                this.locale = new Locale(nuevo);
                FacesContext.getCurrentInstance().getViewRoot().setLocale(this.locale);
            }
        });
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Map<String, String> getLenguajesSoportados() {
        return lenguajesSoportados;
    }

    public void setLenguajesSoportados(Map<String, String> lenguajesSoportados) {
        this.lenguajesSoportados = lenguajesSoportados;
    }
}
