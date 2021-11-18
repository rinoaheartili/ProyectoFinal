/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dominio.DAOEntidadInterfaces;

import modelo.dominio.DTOEntidad.Cliente;

/**
 *
 * @author Alberto
 */
public interface IDAOCliente 
{
    public void Agregar(Cliente cliente);
    public void Eliminar(int id);
    public void Modificar(int id, Cliente cliente);
    public void ObtenerDatos();
    public Cliente Buscar(int id);
}
