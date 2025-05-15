package com.product.api.dto.out;

public class InfoPaginacion {

    // Versión de serialización para compatibilidad
    private static final long serialVersionUID = 1502769119951843826L;
    
    // Número de la página actual
    private Integer paginaActual;
    // Indica si existe una página siguiente
    private Boolean paginaSiguiente;
    // Indica si existe una página anterior
    private Boolean paginaAnterior;
    // Total de páginas disponibles
    private Integer paginasTotales;
    // Cantidad de registros devueltos en esta página
    private Integer registrosDevueltos;
    // Cantidad total de registros en todas las páginas
    private Long registrosTotales;

    // Constructor por defecto
    public InfoPaginacion() {
    }

    /**
     * Constructor completo para inicializar todos los campos de paginación.
     */
    public InfoPaginacion(Integer paginaActual,
                          Boolean paginaSiguiente,
                          Boolean paginaAnterior,
                          Integer paginasTotales,
                          Integer registrosDevueltos,
                          Long registrosTotales) {
        this.paginaActual = paginaActual;
        this.paginaSiguiente = paginaSiguiente;
        this.paginaAnterior = paginaAnterior;
        this.paginasTotales = paginasTotales;
        this.registrosDevueltos = registrosDevueltos;
        this.registrosTotales = registrosTotales;
    }

    // Devuelve la página actual
    public Integer getPaginaActual() {
        return paginaActual;
    }

    // Asigna la página actual
    public void setPaginaActual(Integer paginaActual) {
        this.paginaActual = paginaActual;
    }

    // Devuelve si hay página siguiente
    public Boolean getPaginaSiguiente() {
        return paginaSiguiente;
    }

    // Asigna la existencia de página siguiente
    public void setPaginaSiguiente(Boolean paginaSiguiente) {
        this.paginaSiguiente = paginaSiguiente;
    }

    // Devuelve si hay página anterior
    public Boolean getPaginaAnterior() {
        return paginaAnterior;
    }

    // Asigna la existencia de página anterior
    public void setPaginaAnterior(Boolean paginaAnterior) {
        this.paginaAnterior = paginaAnterior;
    }

    // Devuelve el total de páginas
    public Integer getPaginasTotales() {
        return paginasTotales;
    }

    // Asigna el total de páginas
    public void setPaginasTotales(Integer paginasTotales) {
        this.paginasTotales = paginasTotales;
    }

    // Devuelve el número de registros devueltos en la página actual
    public Integer getRegistrosDevueltos() {
        return registrosDevueltos;
    }

    // Asigna el número de registros devueltos
    public void setRegistrosDevueltos(Integer registrosDevueltos) {
        this.registrosDevueltos = registrosDevueltos;
    }

    // Devuelve el total de registros en todas las páginas
    public Long getRegistrosTotales() {
        return registrosTotales;
    }

    // Asigna el total de registros disponibles
    public void setRegistrosTotales(Long registrosTotales) {
        this.registrosTotales = registrosTotales;
    }

    // Devuelve el serialVersionUID
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
}