package src.main.java.web;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import src.main.java.dao.UtilisateurRepository;
import src.main.java.entities.Categorie;
import src.main.java.entities.Employe;
import src.main.java.entities.RendezVous;
import src.main.java.entities.Service;
import src.main.java.entities.Utilisateur;
import src.main.java.metier.IPlateformeMetier;

@Controller
public class PlateformeControleur  {
	
	@Autowired
	private IPlateformeMetier iPlateformeMetier;
	@Autowired
	private UtilisateurRepository utilisateurRepository;
	
	@RequestMapping("/home") // qd je vais tapper localhost/home je vais avoir une vue qui s appelle home
	public String index(ModelMap model) {
		List<Categorie> categories= iPlateformeMetier.getAllCat();
	    model.put("categorieList", categories);
		List<Service> ls= iPlateformeMetier.getServices();
		model.put("serviceList", ls);
		return "client/home";
	}
	@RequestMapping("/register") 
	public String client() {
		return "/user/register";
	}
	@RequestMapping("/registeEmp") 
	public String employee() {
		return "/user/registerEmp";
	}
	
	
	@RequestMapping("/users") 
	public String test(ModelMap model) {
		try {
			
			List<Utilisateur> us= iPlateformeMetier.getAllUsers();
		    model.put("listUsers", us);
		    for(Utilisateur t:us) {
		    	System.out.println("******"+t.getNom());
		    }
			
		} catch (Exception e) {
		

			model.addAttribute("exception", e);
		}
		return "admin/users";
	}
	@RequestMapping("/monCompte") 
	public String myAccount(ModelMap model) {
		
		Utilisateur u= utilisateurRepository.findByEmail(iPlateformeMetier.currentUser());
		model.put("user", u);
		//int role= 2;
		//System.out.println("/////////////"+utilisateurRepository.role(u));
		//model.put("role", role);
		return "user_auth/register";
	}
	@RequestMapping("/getService") 
	public String getService(ModelMap model ,String designation, Categorie categorie) {
		List<Categorie> categories= iPlateformeMetier.getAllCat();
	    model.put("categorieList", categories);
		
		try {
			List<Utilisateur> us= iPlateformeMetier.getAllUsers();
		    model.put("listUsers", us);
		    for(Utilisateur t:us) {
		    	System.out.println("****"+t.getNom());
		    }
			
			List<Service> s= iPlateformeMetier.getService(designation, categorie);
			 for(Service t:s) {
			    	System.out.println("****SEARCH"+t.getDesignation());
			    }
		    model.put("serviceList", s);
			
		} catch (Exception e) {
		

			model.addAttribute("exception", e);
		}
		
		return "client/home";
	}

	@RequestMapping("/getUser") 
	public String afficher(ModelMap model ,String cin) {
		
		try {
			List<Utilisateur> us= iPlateformeMetier.getAllUsers();
		    model.put("listUsers", us);
		    for(Utilisateur t:us) {
		    	System.out.println("****"+t.getNom());
		    }
			
			Utilisateur u= iPlateformeMetier.getUser(cin);
		    model.put("user", u);
			System.out.println("**********************/////////////////********"+u.getCin());
			
		} catch (Exception e) {
		

			model.addAttribute("exception", e);
		}
		
		return "home";
	}
	
	@RequestMapping("/user" ) 
	public String listUsers(ModelMap model) {
		
		try {
		
			List<Utilisateur> us= iPlateformeMetier.getAllUsers();
		    model.put("listUsers", us);
		    for(Utilisateur t:us) {
		    	System.out.println("******"+t.getNom());
		    }
			
		} catch (Exception e) {
		

			model.addAttribute("exception", e);
		}
		
		return "home";
	}
	
	  @InitBinder public void initBinder(WebDataBinder binder) {
	  binder.registerCustomEditor(Date.class, new CustomDateEditor(new
	  SimpleDateFormat("yyyy-MM-dd'T'hh:mm"),true)); }
	 
	
	@RequestMapping(value="/addUser", method = RequestMethod.POST ) 
	public String ajouterUtilisateur(ModelMap model ,String cin, String nom, String prenom, String adresse, String mdp, Boolean active,String email, String tel,	@RequestParam(name = "DateDeNaissance", defaultValue = "1900-01-01") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate DateDeNaissance
) {
		
		try {
			iPlateformeMetier.addUser(cin, nom, prenom, adresse, DateDeNaissance, mdp,active,email,tel);
			System.out.println("'*******************************"+DateDeNaissance);
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "home";
	}
	@RequestMapping("/edit/{cin}")
	public ModelAndView editUser(@PathVariable(name="cin") String cin) {
	    ModelAndView mav= new ModelAndView("edit_user");
	   
	    Utilisateur user= iPlateformeMetier.getUser(cin);
	    mav.addObject("user", user);
	    return mav;
	}
	@RequestMapping("/delete/{cin}")
	public String deleteUser(@PathVariable(name="cin") String cin) {
	    iPlateformeMetier.SupprimerUtilisateur(cin);
	    return "user";
	}
	
	//RDV
	
	@RequestMapping("/mesRdv") 
	public String mesRDVs(ModelMap model) {
		List<RendezVous> lr= iPlateformeMetier.mesRdv();
	    model.put("listRdv", lr);
		return "rdv/mesRdv";
	}
	/*
	 * @RequestMapping("/mesRdv") public String rdvClient(ModelMap model) {
	 * List<RendezVous> lr= iPlateformeMetier.mesRdv(); model.put("listRdv", lr);
	 * return "rdv/mesRdv"; }
	 */
	
	@RequestMapping("/rdvs") 
	public String mesRDV(ModelMap model) {
		List<RendezVous> lr= iPlateformeMetier.getAllRdv();
	    model.put("listRdv", lr);
		return "rdv/tousRdv";
	}
	
	@RequestMapping(value="/addApp", method = RequestMethod.POST ) 
	public String ajouterRDV(ModelMap model ,Utilisateur utilisateur ,Service service, Date date)
	{

		try {
			iPlateformeMetier.addRdv(utilisateur,service,date);
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		
		 return "redirect:/home";
	}
	
	@RequestMapping("/editApp/{id}")
	public ModelAndView editApp(@PathVariable(name="id") long id,ModelMap model) {
		List<Utilisateur> lr= iPlateformeMetier.getAllUsers();
	   
	    List<Service> ls= iPlateformeMetier.getAllServices();
	    
	    List<Categorie> lc= iPlateformeMetier.getAllCat();
	    
	    ModelAndView mav= new ModelAndView("rdv/edit_rdv");
	   
	    RendezVous rdv= iPlateformeMetier.getRdv(id);
	    mav.addObject("categorieList", lc);
	    mav.addObject("preList", lr);
	    mav.addObject("serviceList", ls);
	    mav.addObject("rdv", rdv);
	    
	    
	    return mav;
	}
	@RequestMapping("/deleteApp/{id}")
	public String deleteRdv(@PathVariable(name="id") long id, HttpServletRequest request) {
	    iPlateformeMetier.SupprimerRdv(id);
	    String referer = request.getHeader("Referer");
	    return "redirect:"+ referer;
	    
	}
	@RequestMapping("/confirmer/{id}")
	public String confirmeApp(@PathVariable(name="id") long id) {
	
		 iPlateformeMetier.ConfirmerRdv(id);
	    return "redirect:/serviceHome";
	}

	
	///Service
	@RequestMapping("/service") 
	public String service(ModelMap model) {
		List<Categorie> ls= iPlateformeMetier.getAllCat();
	    model.put("categorieList", ls);
		return "service/mesServices";
	}
	@RequestMapping(value="/addService", method = RequestMethod.POST ) 
	public String ajouterService(ModelMap model ,Categorie categorie ,String designation, int duree,double prix)
	{
		System.out.println("'******************************* modif");

		try {
			iPlateformeMetier.addService(categorie,designation,duree,prix);
		} catch (Exception e) {
			model.addAttribute("exception", e);
		}
		
		return "redirect:/mesServices";
	}
	@RequestMapping("/mesServices") 
	public String mesServices(ModelMap model) {
		List<Categorie> lc= iPlateformeMetier.getAllCat();
		Service service= new Service();
	    model.put("categorieList", lc);
	    model.put("service", service);
		List<Service> ls= iPlateformeMetier.mesServices();
		/* List<Service> ls= iPlateformeMetier.getAllServices(); */
	    model.put("listServices", ls);
		return "service/mesServices";
	}
	
	@RequestMapping("/editService/{id}")
	public ModelAndView editService(@PathVariable(name="id") long id, ModelMap model) {
		List<Categorie> ls= iPlateformeMetier.getAllCat();
	    ModelAndView mav= new ModelAndView("service/mesServices");
	   
	    Service service= iPlateformeMetier.getService(id);
	    mav.addObject("categorieList", ls);

	    mav.addObject("service", service);
	    return mav;
	}
	@RequestMapping("/deleteService/{id}")
	public String deleteService(@PathVariable(name="id") long id) {
	    iPlateformeMetier.SupprimerService(id);
	    
	    return "redirect:/mesServices";
	}
	
	

}
