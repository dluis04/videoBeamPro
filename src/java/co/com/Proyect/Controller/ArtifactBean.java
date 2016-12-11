/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.Proyect.Controller;

import co.com.Proyect.Model.Artefacto;
import co.com.Proyect.Model.Estado;
import co.com.Proyect.Model.Marca;
import co.com.Proyect.Model.Tipoartefacto;
import java.util.ArrayList;
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
public class ArtifactBean {

    private Artefacto artefacto;
    private List<Artefacto> ListArtefacto;
    private List<Artefacto> ListArtefactoFiltro;
    private List<SelectItem> listMarca;
    private List<SelectItem> listEstado;
    private List<SelectItem> listArtefactoItem;
    private int idMarca;
    private int idEstado;
    private int idArtefacto;

    public ArtifactBean() {
        artefacto = new Artefacto();
        fetcArtefacto();
        llenarCombos();
    }

    private List<Tipoartefacto> fetcTipoArtefacto() {
        List<Tipoartefacto> list = new ArrayList();
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Tipoartefacto.findAll");
            list = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private void fetcArtefacto() {
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Artefacto.findAll");
            ListArtefacto = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void registrar() {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        entityManager.getTransaction().begin();
        artefacto.setIdArtefacto(ListArtefacto.size() + 1);
        Marca marca = new Marca();
        Estado estado = new Estado();
        Tipoartefacto tipo = new Tipoartefacto();
        marca.setIdMarca(idMarca);
        tipo.setIdTipoartefacto(1);
        estado.setIdEstado(idEstado);
        artefacto.setIdMarca(marca);
        artefacto.setIdEstado(estado);
        artefacto.setIdTipoartefacto(tipo);
        entityManager.persist(artefacto);
        entityManager.getTransaction().commit();
        artefacto = null;
        artefacto = new Artefacto();
        fetcArtefacto();
    }

    public void llenarCombos() {

        listMarca = new ArrayList();
        listEstado = new ArrayList();
        listArtefactoItem = new ArrayList();

        for (Marca objMarca : fetcMarcas()) {
            listMarca.add(new SelectItem(objMarca.getIdMarca(), objMarca.getDescripcion()));
        }

        for (Estado objEstado : fetcEstados()) {
            listEstado.add(new SelectItem(objEstado.getIdEstado(), objEstado.getDescripcion()));
        }

        for (Tipoartefacto objArtefacto : fetcTipoArtefacto()) {
            listArtefactoItem.add(new SelectItem(objArtefacto.getIdTipoartefacto(), objArtefacto.getDescripcion()));
        }
    }

    private List<Estado> fetcEstados() {
        List<Estado> list = new ArrayList();
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Estado.findAll");
            list = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private List<Marca> fetcMarcas() {
        List<Marca> list = new ArrayList();
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Marca.findAll");
            list = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public String seleccionEstado(int id) {
        String select;
        Estado obj = new Estado();
        for (Estado esta : fetcEstados()) {

            if (esta.getIdEstado().equals(id)) {
                obj = esta;
                break;
            }
        }
        select = obj.getDescripcion();
        return select;
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

    /**
     * Evento para editar docente en la fila de una tabla.
     *
     * @param event Evento.
     *
     */
    public void onRowEdit(RowEditEvent event) {
//        tipoartefacto = ((Tipoartefacto) event.getObject());
//        FacesMessage msg = new FacesMessage("Artefacto Actualizad0", "");
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        actualizarTable(tipoartefacto);
//        tipoartefacto = new Tipoartefacto();
    }

    public void eliminar(Artefacto c) {
//        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
//        Query query = entityManager.createNamedQuery("Tipoartefacto.findByIdTipoartefacto");
//        query.setParameter("idTipoartefacto", c.getIdTipoartefacto());
//        Tipoartefacto tipArt = (Tipoartefacto) query.getSingleResult();
//        entityManager.getTransaction().begin();
//
//        entityManager.remove(tipArt);
//
//        entityManager.getTransaction().commit();
//        fetctipoArtefacto();
//        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipo Artefacto eliminado", "");
//        FacesContext.getCurrentInstance().addMessage(null, msg);

    }

    public Artefacto getArtefacto() {
        return artefacto;
    }

    public void setArtefacto(Artefacto artefacto) {
        this.artefacto = artefacto;
    }

    public List<Artefacto> getListArtefacto() {
        return ListArtefacto;
    }

    public void setListArtefacto(List<Artefacto> ListArtefacto) {
        this.ListArtefacto = ListArtefacto;
    }

    public List<SelectItem> getListMarca() {
        return listMarca;
    }

    public void setListMarca(List<SelectItem> listMarca) {
        this.listMarca = listMarca;
    }

    public List<SelectItem> getListEstado() {
        return listEstado;
    }

    public void setListEstado(List<SelectItem> listEstado) {
        this.listEstado = listEstado;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public List<Artefacto> getListArtefactoFiltro() {
        return ListArtefactoFiltro;
    }

    public void setListArtefactoFiltro(List<Artefacto> ListArtefactoFiltro) {
        this.ListArtefactoFiltro = ListArtefactoFiltro;
    }

    public int getIdArtefacto() {
        return idArtefacto;
    }

    public void setIdArtefacto(int idArtefacto) {
        this.idArtefacto = idArtefacto;
    }

    public List<SelectItem> getListArtefactoItem() {
        return listArtefactoItem;
    }

    public void setListArtefactoItem(List<SelectItem> listArtefactoItem) {
        this.listArtefactoItem = listArtefactoItem;
    }

}
