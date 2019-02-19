package com.bolsadeideas.springboot.app.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/index")
	public String index(Map<String, Object> model) {

		model.put("titulo", "Nutricionista y Dietista");
		model.put("subtitulo",
				"Cada vez somos más los que nos dedicamos a la nutrición y cada vez son más los que se interesan por su salud así que entre todos, y con estos consejos que les voy a ir dando, vamos a hacer de nuestra salud algo extraordinario. ");
		model.put("dato",
				"La finalidad de este sitio es adentraros en el fabuloso mundo de la nutrición, donde les entregare consejos, recomendaciones, artículos de interés y recetas para que entiendan que comer sano no implica que sea aburrido y para que vean como un estilo de vida saludable puede encajar perfectamente con todo tipo de situaciones que se nos presenten.");
		model.put("dato2","¿ Y tú, te animas a cambiar tus hábitos y a empezar un estilo de vida saludable?");
		return "index";
	}
}
