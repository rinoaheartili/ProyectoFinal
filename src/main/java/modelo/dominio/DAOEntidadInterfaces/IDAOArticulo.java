/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.DAOEntidadInterfaces;

import modelo.dominio.DTOEntidad.Articulo;

/**
 *
 * @author Alberto
 */
public interface IDAOArticulo 
{
    public void Agregar(Articulo articulo);
    public void Eliminar(int id);
    public void Modificar(int id, Articulo articulo);
    public Articulo Buscar(int id);
    public void ObtenerDatos();
}
