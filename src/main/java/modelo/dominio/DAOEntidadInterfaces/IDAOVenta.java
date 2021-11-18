/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.DAOEntidadInterfaces;

import modelo.dominio.DTOEntidad.Venta;

/**
 *
 * @author Alberto
 */
public interface IDAOVenta 
{
    public void Agregar(Venta venta);
    public void Eliminar(int id);
    public void Modificar(int id, Venta venta);
    public Venta Buscar(int id);
    public void ObtenerDatos();
}
