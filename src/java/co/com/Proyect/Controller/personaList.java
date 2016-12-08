/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.Proyect.Controller;

import co.com.Proyect.Model.Persona;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author USER
 */
@ManagedBean(name = "personaList")
@RequestScoped

public class personaList {

    private List<Persona> PersonList;

    public personaList() {
        fetcPerson();
    }

    private void fetcPerson() {
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Persona.findAll");
            PersonList = query.getResultList();
            System.out.println(PersonList.size());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Persona> getPersonList() {
        return PersonList;
    }

    public void setPersonList(List<Persona> PersonList) {
        this.PersonList = PersonList;
    }

}
