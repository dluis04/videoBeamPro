/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.com.Proyect.Controller;

import co.com.Proyect.Model.Artefacto;
import co.com.Proyect.Model.Estado;
import co.com.Proyect.Model.Persona;
import co.com.Proyect.Model.Reserva;
import co.com.Proyect.Model.Rol;
import java.util.ArrayList;
import java.util.Date;
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
public class ReservasBean {

    private Date fechaDesde;
    private Date fechaHasta;
    private Reserva reserva;
    private List<Artefacto> ListArtefacto;
    private List<Artefacto> ListArtefactoFiltro;
    private Artefacto selectArtefacto;
    private Persona selectPersona;
    private List<Persona> docenteList;
    private List<Persona> docenteListFiltro;
    private List<SelectItem> listEstado;
    private int idEstado;

    public ReservasBean() {
        reserva = new Reserva();
        selectPersona = null;
        selectArtefacto = null;
        fetcArtefacto();
        fetcPerson();
        llenarCombos();
    }

    private void fetcArtefacto() {
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Artefacto.findAll");
            ListArtefacto = query.getResultList();
            System.out.println("Entro artefacto");
        } catch (Exception e) {
            e.printStackTrace();
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

    private List<Reserva> fetcReserva() {
        List<Reserva> list = new ArrayList();
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Reserva.findAll");
            list = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void registrar() {

        if (selectArtefacto != null && selectPersona != null) {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            String descripcionEstado = selectArtefacto.getIdEstado().getDescripcion();
            if (descripcionEstado.equalsIgnoreCase("Reservado") || descripcionEstado.equalsIgnoreCase("Inactivo") || descripcionEstado.equalsIgnoreCase("En Reparacion")) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "El artefacto se encuentra " + descripcionEstado + " No se puede reservar", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } else {
                int horas = ((fechaDesde.getHours() - fechaHasta.getHours()) * -1);
                int minutos = (fechaDesde.getMinutes() + fechaHasta.getMinutes());
                int total = 0;

                if (minutos == 60) {
                    total = (selectArtefacto.getHorasenoperacion() + horas + 1);
                } else {
                    total = (selectArtefacto.getHorasenoperacion() + horas);
                }

                if (total > selectArtefacto.getIdMarca().getHoraslimitesoperacion()) {
                    entityManager.getTransaction().begin();
                    reserva.setIdReserva(fetcReserva().size() + 1);
                    reserva.setHorainicio(fechaDesde);
                    reserva.setHorafin(fechaHasta);
                    reserva.setNumeroId(selectPersona);
                    reserva.setIdArtefacto(selectArtefacto);
                    entityManager.persist(reserva);
                    entityManager.getTransaction().commit();
                    actualizarArtefactoCiclo(selectArtefacto.getIdArtefacto());
                    selectPersona = null;
                    selectArtefacto = null;
                    reserva = null;
                    selectPersona = new Persona();
                    selectArtefacto = new Artefacto();
                    reserva = new Reserva();
                    fechaDesde = null;
                    fechaHasta = null;
                    fetcArtefacto();
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Se Desconto " + ((selectArtefacto.getIdMarca().getHoraslimitesoperacion() - total) * -1) + " horas del tiempo, ya que el artefacto cumple su ciclo de uso.", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                } else {
                    entityManager.getTransaction().begin();
                    reserva.setIdReserva(fetcReserva().size() + 1);
                    reserva.setHorainicio(fechaDesde);
                    reserva.setHorafin(fechaHasta);
                    reserva.setNumeroId(selectPersona);
                    reserva.setIdArtefacto(selectArtefacto);
                    entityManager.persist(reserva);
                    entityManager.getTransaction().commit();
                    actualizarArtefactoEstado(selectArtefacto.getIdArtefacto());
                    selectPersona = null;
                    selectArtefacto = null;
                    reserva = null;
                    reserva = new Reserva();
                    fechaDesde = null;
                    fechaHasta = null;
                    fetcArtefacto();
                    FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reserva exitosa", "");
                    FacesContext.getCurrentInstance().addMessage(null, msg);
                }
            }
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Debe seleccionar las opciones de artefacto y persona para realizar la reserva", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void actualizarArtefactoCiclo(int idArtefacto) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        Query query = entityManager.createNamedQuery("Artefacto.findByIdArtefacto");

        query.setParameter("idArtefacto", idArtefacto);
        Artefacto art = (Artefacto) query.getSingleResult();
        art.setCiclouso(art.getCiclouso() + 1);
        art.setHorasenoperacion(0);
        Estado estado = new Estado();
        estado.setIdEstado(4);
        entityManager.getTransaction().begin();
        art.setIdEstado(estado);
        entityManager.merge(art);
        entityManager.getTransaction().commit();
    }

    public void actualizarArtefactoEstado(int idArtefacto) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        Query query = entityManager.createNamedQuery("Artefacto.findByIdArtefacto");

        query.setParameter("idArtefacto", idArtefacto);
        Artefacto art = (Artefacto) query.getSingleResult();
        int horas = ((fechaDesde.getHours() - fechaHasta.getHours()) * -1);
        int minutos = (fechaDesde.getMinutes() + fechaHasta.getMinutes());
        if (minutos == 60) {
            art.setHorasenoperacion(art.getHorasenoperacion() + horas + 1);
        } else {
            art.setHorasenoperacion(art.getHorasenoperacion() + horas);
        }

        Estado estado = new Estado();
        estado.setIdEstado(3);
        entityManager.getTransaction().begin();
        art.setIdEstado(estado);
        entityManager.merge(art);
        entityManager.getTransaction().commit();
//        UsuarioDaoImpl Usuariodao = new UsuarioDaoImpl();
//        Usuariodao.update(usuario);
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

    private List<Rol> fetcRol() {
        List<Rol> roles = new ArrayList();
        try {
            EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
            Query query = entityManager.createNamedQuery("Rol.findAll");
            roles = query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return roles;
    }

    public void llenarCombos() {
        listEstado = new ArrayList();

        for (Estado objEstado : fetcEstados()) {
            listEstado.add(new SelectItem(objEstado.getIdEstado(), objEstado.getDescripcion()));
        }
    }

    public void onRowEdit(RowEditEvent event) {
        Artefacto artefacto = ((Artefacto) event.getObject());
        FacesMessage msg = new FacesMessage("Artefacto Actualizado", "");

        if (artefacto.getIdEstado().getIdEstado() == 1 || artefacto.getIdEstado().getIdEstado() == 2) {
            artefacto.setHorasenoperacion(0);
        }
        FacesContext.getCurrentInstance().addMessage(null, msg);
        actualizarTable(artefacto);
    }

    public void actualizarTable(Artefacto Artf) {
        EntityManager entityManager = Persistence.createEntityManagerFactory("ProyectoFinalPU").createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.merge(Artf);
        entityManager.getTransaction().commit();
//        UsuarioDaoImpl Usuariodao = new UsuarioDaoImpl();
//        Usuariodao.update(usuario);
    }

    public Date getFechaDesde() {
        return fechaDesde;
    }

    public void setFechaDesde(Date fechaDesde) {
        this.fechaDesde = fechaDesde;
    }

    public Date getFechaHasta() {
        return fechaHasta;
    }

    public void setFechaHasta(Date fechaHasta) {
        this.fechaHasta = fechaHasta;
    }

    public List<Artefacto> getListArtefacto() {
        return ListArtefacto;
    }

    public void setListArtefacto(List<Artefacto> ListArtefacto) {
        this.ListArtefacto = ListArtefacto;
    }

    public List<Artefacto> getListArtefactoFiltro() {
        return ListArtefactoFiltro;
    }

    public void setListArtefactoFiltro(List<Artefacto> ListArtefactoFiltro) {
        this.ListArtefactoFiltro = ListArtefactoFiltro;
    }

    public Artefacto getSelectArtefacto() {
        return selectArtefacto;
    }

    public void setSelectArtefacto(Artefacto selectArtefacto) {
        this.selectArtefacto = selectArtefacto;
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

    public Persona getSelectPersona() {
        return selectPersona;
    }

    public void setSelectPersona(Persona selectPersona) {
        this.selectPersona = selectPersona;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    public List<SelectItem> getListEstado() {
        return listEstado;
    }

    public void setListEstado(List<SelectItem> listEstado) {
        this.listEstado = listEstado;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

}
