package com.bolsadeideas.springboot.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bolsadeideas.springboot.app.models.dao.IClienteDao;
import com.bolsadeideas.springboot.app.models.dao.IHoraDao;
import com.bolsadeideas.springboot.app.models.dao.IServicioDao;
import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Hora;
import com.bolsadeideas.springboot.app.models.entity.Servicio;

@Service
public class ClienteServiceImpl implements IClienteService{

	@Autowired
	private IClienteDao clienteDao;
	
	@Autowired
	private IServicioDao servicioDao;
	
	@Autowired
	private IHoraDao horaDao;
	
	@Override
	@Transactional(readOnly = true)
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return (List<Cliente>) clienteDao.findAll();
	}

	@Override
	@Transactional
	public void save(Cliente cliente) {
		clienteDao.save(cliente);
	}

	@Override
	@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return clienteDao.findById(id).orElse(null);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Cliente fetchByIdWithHoras(Long id) {
		return clienteDao.fetchByIdWithHoras(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		clienteDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Page<Cliente> findAll(Pageable pageable) {
		return clienteDao.findAll(pageable);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Servicio> findByNombre(String term) {
		return servicioDao.findByNombreLikeIgnoreCase("%"+term+"%");
	}

	@Override
	@Transactional
	public void saveHora(Hora hora) {
		horaDao.save(hora);
	}

	@Override
	@Transactional(readOnly=true)
	public Servicio findServicioById(Long id) {
		return servicioDao.findById(id).orElse(null);
	}

	@Override
	@Transactional(readOnly=true)
	public Hora findHoraById(Long id) {
		return horaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void deleteHora(Long id) {
		horaDao.deleteById(id); // horaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly=true)
	public Hora fetchHoraByIdWithClienteWhithItemHoraWithServicio(Long id) {
		return horaDao.fetchByIdWithClienteWhithItemHoraWithServicio(id);
	}
	
	
}
