/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.Proyect.Controller;

import co.com.Proyect.Model.Persona;
import co.com.Proyect.Model.Rol;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Bryan
 */
@ManagedBean
@RequestScoped
public class DocenteBean {

    private Persona docente;
    private Persona docenteTabla;
    private List<Persona> docenteList;
    private List<Persona> docenteListFiltro;
    private List<SelectItem> listRol;
    private List<Rol> roles;
    private int idRol;

    public DocenteBean() {
        docente = new Persona();
        docenteTabla = new Persona();
        fetcPerson();
        listRol = new ArrayList();
        
        for (Rol obj : fetcRol()) {
            listRol.add(new SelectItem(obj.getIdRol(), obj.getDescripcion()));
            System.out.println("Descripcion -->> "+obj.getDescripcion());
        }

    }

    private void fetcPerson() {
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Persona.findAll");
            docenteList = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Rol> fetcRol() {
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Rol.findAll");
            roles = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }

    public void registrar() {

        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        entityManager.getTransaction().begin();
        Rol x = new Rol();
        x.setIdRol(1);
        docente.setIdRol(x);
        entityManager.persist(docente);
        entityManager.getTransaction().commit();
        docente = new Persona();
    }

    public void eliminar(Persona c) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        Query query = entityManager.createNamedQuery("Persona.findByNumeroId");
        query.setParameter("numeroId", c.getNumeroId());
        Persona Person = (Persona) query.getSingleResult();
        entityManager.getTransaction().begin();

        entityManager.remove(Person);

        entityManager.getTransaction().commit();
        fetcPerson();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Persona eliminada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public String seleccion(int id) {
        String select;
        Rol obj = new Rol();
        for (Rol rol : fetcRol()) {

            if (rol.getIdRol().equals(id)) {
                obj = rol;
                break;
            }
        }
        select = obj.getDescripcion();
        return select;
    }

    /**
     * Evento para editar docente en la fila de una tabla.
     *
     * @param event Evento.
     *
     */
    public void onRowEdit(RowEditEvent event) {
        docente = ((Persona) event.getObject());
        System.out.println("Nombre " + ((Persona) event.getObject()).getNombre());
        FacesMessage msg = new FacesMessage("Persona Actualizada", ((Persona) event.getObject()).getNombre() + " " + ((Persona) event.getObject()).getApellido());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        actualizarTable(docente);
        docente = new Persona();
    }

    public void actualizarTable(Persona usuario) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(usuario);
        entityManager.getTransaction().commit();
//        UsuarioDaoImpl Usuariodao = new UsuarioDaoImpl();
//        Usuariodao.update(usuario);
    }

    /**
     * Evento para cancelar la edicion de un docente en una fila de una tabla.
     *
     * @param event Evento.
     *
     */
    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Cancelado", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Persona getDocente() {
        return docente;
    }

    public void setDocente(Persona docente) {
        this.docente = docente;
    }

    public Persona getDocenteTabla() {
        return docenteTabla;
    }

    public void setDocenteTabla(Persona docenteTabla) {
        this.docenteTabla = docenteTabla;
    }

    public List<Persona> getDocenteList() {
        return docenteList;
    }

    public void setDocenteList(List<Persona> docenteList) {
        this.docenteList = docenteList;
    }

    public List<Persona> getDocenteListFiltro() {
        return docenteListFiltro;
    }

    public void setDocenteListFiltro(List<Persona> docenteListFiltro) {
        this.docenteListFiltro = docenteListFiltro;
    }

    public List<SelectItem> getListRol() {
        return listRol;
    }

    public void setListRol(List<SelectItem> listRol) {
        this.listRol = listRol;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

}
