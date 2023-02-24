package org.olmedo.webapp.jsf3.controllers;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.inject.Model;
import jakarta.enterprise.inject.Produces;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.olmedo.webapp.jsf3.entities.Categoria;
import org.olmedo.webapp.jsf3.entities.Producto;
import org.olmedo.webapp.jsf3.services.ProductoService;

import java.util.List;
import java.util.ResourceBundle;

@Model // --> es un estereotype que junta @Named y @RequestScope es tipica para usar un cotrolador
public class ProductoController {

    private Producto producto;

    private Long id;

    @Inject
    private ProductoService service;

    @Inject
    private FacesContext facesContext;

    @Inject
    private ResourceBundle bundle;

    @Produces
    @Model /*--> por defecto el model va a tomar el nombre del metodo o de la clase si lo queremos
    modificar ahi si tenemos que usar el @Named y @RequestScope*/
    public String titulo() {
        //return "Hola mundo JavaServer Face 3.0";
        return bundle.getString("producto.texto.titulo");
    }

    @Produces
    @RequestScoped
    @Named("listado")
    public List<Producto> findAll() {
        //    return Arrays.asList(new Producto("peras"), new Producto("manzanas"), new Producto("mandarinas"));
        return service.listar();
    }

    @Produces
    @Model
    public Producto producto() {
        this.producto = new Producto();
        if (id != null && id > 0) {
            //producto = service.porId(id).orElseThrow();
            service.porId(id).ifPresent(p -> {
                this.producto = p;
            });
        }
        return producto;
    }

    @Produces
    @Model
    public List<Categoria> categorias(){
        return service.listarCategorias();
    }

    public String editar(Long id) {
        this.id = id;
        return "form.xhtml";
    }

    public String guardar() {
        System.out.println(producto);
        service.guardar(producto);
        if (producto.getId() != null && producto.getId() > 0) {
            facesContext.addMessage(null, new FacesMessage(String.format(bundle.getString("producto.mensaje.editar"), producto.getNombre())));
        } else {
            facesContext.addMessage(null, new FacesMessage(String.format(bundle.getString("producto.mensaje.crear"), producto.getNombre())));
        }
        return "index.xhtml?faces-redirect=true"; // manejo de navegacion en jsf vuelve al index despues de guardar
    }

    public String eliminar(Producto producto) {
        service.eliminar(producto.getId());
        facesContext.addMessage(null, new FacesMessage(String.format(bundle.getString("producto.mensaje.eliminar"), producto.getNombre())));
        return "index.xhtml?faces-redirect=true";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}