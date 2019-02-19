package com.bolsadeideas.springboot.app.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsadeideas.springboot.app.models.entity.Cliente;
import com.bolsadeideas.springboot.app.models.entity.Hora;
import com.bolsadeideas.springboot.app.models.entity.ItemHora;
import com.bolsadeideas.springboot.app.models.entity.Servicio;
import com.bolsadeideas.springboot.app.models.service.IClienteService;

@Secured("ROLE_ADMIN")
@Controller
@RequestMapping("/hora")
@SessionAttributes("hora")
public class HoraController {

	@Autowired
	private IClienteService clienteService;



	@GetMapping("/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Model model, RedirectAttributes flash) {

		Hora hora = clienteService.fetchHoraByIdWithClienteWhithItemHoraWithServicio(id); // clienteService.findHoraById(id);

		if (hora == null) {
			flash.addFlashAttribute("error", "La hora no existe en la base de datos!");
			return "redirect:/listar";
		}

		model.addAttribute("hora", hora);
		model.addAttribute("titulo", "Hora: ".concat(hora.getDescripcion()));
		return "hora/ver";
	}

	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Map<String, Object> model,
			RedirectAttributes flash) {

		Cliente cliente = clienteService.findOne(clienteId);

		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}

		Hora hora = new Hora();
		hora.setCliente(cliente);

		model.put("hora", hora);
		model.put("titulo", "Crear Hora");

		return "hora/form";
	}

	@GetMapping(value = "/cargar-servicios/{term}", produces = { "application/json" })
	public @ResponseBody List<Servicio> cargarServicios(@PathVariable String term) {
		return clienteService.findByNombre(term);
	}

	@PostMapping("/form")
	public String guardar(@Valid Hora hora, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			 RedirectAttributes flash,
			SessionStatus status) {

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear Hora");
			return "hora/form";
		}

		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear Hora");
			model.addAttribute("error", "Error: La hora NO puede no tener líneas!");
			return "hora/form";
		}

		for (int i = 0; i < itemId.length; i++) {
			Servicio servicio = clienteService.findServicioById(itemId[i]);

			ItemHora linea = new ItemHora();
			
			linea.setServicio(servicio);
			hora.addItemHora(linea);

			
		}

		clienteService.saveHora(hora);
		status.setComplete();

		flash.addFlashAttribute("success", "Hora creada con éxito!");

		return "redirect:/ver/" + hora.getCliente().getId();
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		Hora hora = clienteService.findHoraById(id);

		if (hora != null) {
			clienteService.deleteHora(id);
			flash.addFlashAttribute("success", "Hora eliminada con éxito!");
			return "redirect:/ver/" + hora.getCliente().getId();
		}
		flash.addFlashAttribute("error", "La reserva de hora no existe en la base de datos, no se pudo eliminar!");

		return "redirect:/listar";
	}

}
