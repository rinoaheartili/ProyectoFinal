/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.DAOEntidadInterfaces;

import modelo.dominio.DTOEntidad.Local;

/**
 *
 * @author Alberto
 */
public interface IDAOLocal 
{
    public void Agregar(Local local);
    public void Eliminar(int id);
    public void Modificar(int id, Local local);
    public void ObtenerDatos();
    public Local Buscar(int id);
}
