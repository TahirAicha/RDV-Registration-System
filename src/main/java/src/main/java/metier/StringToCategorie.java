package src.main.java.metier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import src.main.java.dao.CategorieRepository;
import src.main.java.entities.Categorie;

@Component
public class StringToCategorie implements Converter<String, Categorie>  {
	@Autowired
	private CategorieRepository repository; //Or the class that implments the repository.

	    @Override
	    public Categorie convert(String arg0) {
	        Long id = new Long(arg0);
	        return repository.findById(id).orElse(null);
	    }

}

