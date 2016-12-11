/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.Proyect.Controller;

import co.com.Proyect.Model.Tipoartefacto;
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
public class ArtifactypeBeam {

    private Tipoartefacto tipoartefacto;
    private List<Tipoartefacto> artefactoList;
    private List<Tipoartefacto> artefactoListFiltro;

    public ArtifactypeBeam() {
        tipoartefacto = new Tipoartefacto();
        fetctipoArtefacto();
    }

    private void fetctipoArtefacto() {
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Tipoartefacto.findAll");
            artefactoList = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrar() {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        entityManager.getTransaction().begin();
        tipoartefacto.setIdTipoartefacto(artefactoList.size() + 1);
        entityManager.persist(tipoartefacto);
        entityManager.getTransaction().commit();
        tipoartefacto = null;
        tipoartefacto = new Tipoartefacto();
        fetctipoArtefacto();
    }

    public void eliminar(Tipoartefacto c) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        Query query = entityManager.createNamedQuery("Tipoartefacto.findByIdTipoartefacto");
        query.setParameter("idTipoartefacto", c.getIdTipoartefacto());
        Tipoartefacto tipArt = (Tipoartefacto) query.getSingleResult();
        entityManager.getTransaction().begin();

        entityManager.remove(tipArt);

        entityManager.getTransaction().commit();
        fetctipoArtefacto();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo Artefacto eliminado", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    /**
     * Evento para editar docente en la fila de una tabla.
     *
     * @param event Evento.
     *
     */
    public void onRowEdit(RowEditEvent event) {
        tipoartefacto = ((Tipoartefacto) event.getObject());
        FacesMessage msg = new FacesMessage("Artefacto Actualizad0", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        actualizarTable(tipoartefacto);
        tipoartefacto = new Tipoartefacto();
    }

    public void actualizarTable(Tipoartefacto Artf) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(Artf);
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

    public Tipoartefacto getTipoartefacto() {
        return tipoartefacto;
    }

    public void setTipoartefacto(Tipoartefacto tipoartefacto) {
        this.tipoartefacto = tipoartefacto;
    }

    public List<Tipoartefacto> getArtefactoList() {
        return artefactoList;
    }

    public void setArtefactoList(List<Tipoartefacto> artefactoList) {
        this.artefactoList = artefactoList;
    }

    public List<Tipoartefacto> getArtefactoListFiltro() {
        return artefactoListFiltro;
    }

    public void setArtefactoListFiltro(List<Tipoartefacto> artefactoListFiltro) {
        this.artefactoListFiltro = artefactoListFiltro;
    }

}
