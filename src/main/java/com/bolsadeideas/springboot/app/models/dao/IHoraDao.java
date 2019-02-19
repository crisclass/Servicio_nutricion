package com.bolsadeideas.springboot.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Hora;

public interface IHoraDao extends CrudRepository<Hora, Long>{

	@Query("select f from Hora f join fetch f.cliente c join fetch f.items l join fetch l.servicio where f.id=?1")
	public Hora fetchByIdWithClienteWhithItemHoraWithServicio(Long id);
}
