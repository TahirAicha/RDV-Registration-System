package src.main.java.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import src.main.java.entities.Categorie;



public interface CategorieRepository extends JpaRepository<Categorie, Long> {

}
