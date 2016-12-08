/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.Proyect.Controller;

import co.com.Proyect.Model.Marca;
import co.com.Proyect.Model.Persona;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
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
public class BrandBean {

    private Marca marcaArtefacto;
    private List<Marca> marcaList;
    private List<Marca> marcaListFiltro;

    public BrandBean() {
        marcaArtefacto = new Marca();
        fetcMarca();

    }

    private void fetcMarca() {
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Marca.findAll");
            marcaList = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void eliminar(Marca c) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        Query query = entityManager.createNamedQuery("Marca.findByIdMarca");
        query.setParameter("idMarca", c.getIdMarca());
        Marca marca = (Marca) query.getSingleResult();
        entityManager.getTransaction().begin();
        entityManager.remove(marca);

        entityManager.getTransaction().commit();
        fetcMarca();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Marca eliminada", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public void registrar() {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        entityManager.getTransaction().begin();
        marcaArtefacto.setIdMarca(marcaList.size() + 1);
        entityManager.persist(marcaArtefacto);
        entityManager.getTransaction().commit();
        marcaArtefacto = null;
        marcaArtefacto = new Marca();
        fetcMarca();
    }

    /**
     * Evento para editar docente en la fila de una tabla.
     *
     * @param event Evento.
     *
     */
    public void onRowEdit(RowEditEvent event) {
        marcaArtefacto = ((Marca) event.getObject());
        FacesMessage msg = new FacesMessage("Marca Actualizada", ((Marca) event.getObject()).getDescripcion());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        actualizarTable(marcaArtefacto);
        marcaArtefacto = new Marca();
    }

    public void actualizarTable(Marca marcaUpdate) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(marcaUpdate);
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

    public Marca getMarcaArtefacto() {
        return marcaArtefacto;
    }

    public void setMarcaArtefacto(Marca marcaArtefacto) {
        this.marcaArtefacto = marcaArtefacto;
    }

    public List<Marca> getMarcaList() {
        return marcaList;
    }

    public void setMarcaList(List<Marca> marcaList) {
        this.marcaList = marcaList;
    }

    public List<Marca> getMarcaListFiltro() {
        return marcaListFiltro;
    }

    public void setMarcaListFiltro(List<Marca> marcaListFiltro) {
        this.marcaListFiltro = marcaListFiltro;
    }

}
