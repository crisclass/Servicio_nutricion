package com.bolsadeideas.springboot.app.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bolsadeideas.springboot.app.models.entity.Servicio;

public interface IServicioDao extends CrudRepository<Servicio, Long>{

	@Query("select p from Servicio p where p.nombre like %?1%")
	public List<Servicio> findByNombre(String term);
	
	public List<Servicio> findByNombreLikeIgnoreCase(String term);
}
