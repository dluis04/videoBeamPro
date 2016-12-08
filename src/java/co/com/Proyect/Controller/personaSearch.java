/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.Proyect.Controller;

import co.com.Proyect.Model.Persona;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author USER
 */
@ManagedBean(name = "personaSearch")
@SessionScoped

public class personaSearch {

    private Persona Person;

    public personaSearch() {
        Person = new Persona();
    }

    public String buscarPersona() {
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Persona.findByNumeroId");
            query.setParameter("numeroId", Person.getNumeroId());
            Person = (Persona) query.getSingleResult();
            if (Person != null && Person.getIdRol().getIdRol() == 1) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("MenuAdmin.xhtml");
            } else if (Person != null && Person.getIdRol().getIdRol() == 2) {
                FacesContext.getCurrentInstance().getExternalContext().redirect("MenuUsers.xhtml");
            } else {
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Usuario no encontrado en UNIAJC", "");
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "";
    }

    public Persona getPerson() {
        return Person;
    }

    public void setPerson(Persona Person) {
        this.Person = Person;
    }

}
