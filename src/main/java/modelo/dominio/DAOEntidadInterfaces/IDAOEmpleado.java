/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.DAOEntidadInterfaces;

import modelo.dominio.DTOEntidad.Empleado;

/**
 *
 * @author Alberto
 */
public interface IDAOEmpleado 
{
    public void Agregar(Empleado empleado);
    public void Eliminar(int id);
    public void Modificar(int id, Empleado empleado);
    public Empleado Buscar(int id);
    public void ObtenerDatos();
}
