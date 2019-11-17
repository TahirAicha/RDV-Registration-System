package src.main.java.sec;


import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import src.main.java.dao.ConfirmationTokenRepository;
import src.main.java.dao.UtilisateurRepository;
import src.main.java.entities.Categorie;
import src.main.java.entities.ConfirmationToken;
import src.main.java.entities.Employe;
import src.main.java.entities.RendezVous;
import src.main.java.entities.Service;
import src.main.java.entities.Utilisateur;
import src.main.java.metier.IPlateformeMetier;
@Controller
public class UserController {
	
  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  @Autowired
  IPlateformeMetier iPlateformeMetier;
 @Autowired
 private IPlateformeMetier userService;
 
 @Autowired
 UtilisateurRepository utilisateurRepository;
 
 @Autowired
 private ConfirmationTokenRepository confirmationTokenRepository;

 @Autowired
 private EmailSenderService emailSenderService;
 
 
 @RequestMapping(value= {"/"}, method=RequestMethod.GET)
 public ModelAndView accueil() {
  ModelAndView model = new ModelAndView();
  
  model.setViewName("index");
  return model;
 }

 
 @RequestMapping(value= {"/login"}, method=RequestMethod.GET)
 public ModelAndView login() {
  ModelAndView model = new ModelAndView();
  
  model.setViewName("user_auth/login");
  return model;
 }

 
 @RequestMapping(value= {"/signup"}, method=RequestMethod.GET)
 public ModelAndView signup() {
  ModelAndView model = new ModelAndView();
  Utilisateur user = new Utilisateur();
  model.addObject("user", user);
  model.setViewName("user_auth/register");
  
  return model;
 }
 @RequestMapping(value= {"/s_signup"}, method=RequestMethod.GET)
 public ModelAndView s_signup() {
  ModelAndView model = new ModelAndView();
  Employe emp = new Employe();
  model.addObject("emp", emp);
  model.setViewName("user_auth/registerService");
  
  return model;
 }
 
	/*
	 * @RequestMapping(value= {"/signup"}, method=RequestMethod.POST) public
	 * ModelAndView createUser(@Valid Utilisateur user, BindingResult bindingResult)
	 * { ModelAndView model = new ModelAndView(); Utilisateur userExists =
	 * userService.findUserByEmail(user.getEmail());
	 * 
	 * if(userExists != null) { bindingResult.rejectValue("email", "error.user",
	 * "This email already exists!"); } if(bindingResult.hasErrors()) {
	 * model.setViewName("user_auth/register"); } else { userService.addUser(user);
	 * model.addObject("msg", "User has been registered successfully!");
	 * model.addObject("user", new Utilisateur());
	 * model.setViewName("user_auth/register"); }
	 * 
  return model;
 }
	 */
 
  @RequestMapping(value="/signup", method=RequestMethod.POST)
	public ModelAndView registerUser(@Valid Utilisateur user, BindingResult bindingResult, ModelMap modelMap,@RequestParam(name = "mdp", defaultValue = "") String mdp) {
		ModelAndView modelAndView = new ModelAndView();
		Utilisateur userExists =userService.findUserByEmail(user.getEmail());
		// Check for the validations
		if(bindingResult.hasErrors()) {
			modelAndView.addObject("successMessage", "Please correct the errors in form!");
			modelMap.addAttribute("bindingResult", bindingResult);
		}
		else if(userExists != null){
			modelAndView.addObject("successMessage", "user already exists!");			
		}
		// we will save the user if, no binding errors
		else {
			user.setMdp(bCryptPasswordEncoder.encode(mdp));
			userService.addUser(user);
			ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(user.getEmail());
            mailMessage.setSubject("Confirmer votre adresse email!");
            mailMessage.setFrom("aichatahir@gmail.com");
            mailMessage.setText("Cliquer sur le lien pour confirmer votre adresse email : "
            +"http://localhost:8181/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);

            modelAndView.addObject("emailId", user.getEmail());

            modelAndView.setViewName("successfulRegisteration");
			modelAndView.addObject("successMessage", "Vous etes enregistre! un message de confirmation a ete envoye a ");
			modelAndView.addObject("emailId", user.getEmail());
		}
		
		  modelAndView.addObject("emailId", user.getEmail());
		  modelAndView.addObject("user", new Utilisateur());
		 
		modelAndView.setViewName("user_auth/register");
		return modelAndView;
	}
  	
  @RequestMapping(value="/s_signup", method=RequestMethod.POST)
 	public ModelAndView registerEmp(@Valid Employe user, BindingResult bindingResult, ModelMap modelMap,@RequestParam(name = "mdp", defaultValue = "") String mdp) {
 		ModelAndView modelAndView = new ModelAndView();
 		Utilisateur userExists =userService.findUserByEmail(user.getEmail());
 		// Check for the validations
 		if(bindingResult.hasErrors()) {
 			modelAndView.addObject("successMessage", "corrigez l'erreur en bas de la page!");
 			modelMap.addAttribute("bindingResult", bindingResult);
 		}
 		else if(userExists != null){
 			modelAndView.addObject("successMessage", "Vous etes deja inscrit!");			
 		}
 		// s'il y a pas d'erreur -> enregistrer l utilisateur
 		else {
 			
 			//encoder le mot de passe
 			user.setMdp(bCryptPasswordEncoder.encode(mdp));
 			//ajouter l utilisateur
 			userService.addEmploye(user);
 			//envoyer un mail de confirmation
 			ConfirmationToken confirmationToken = new ConfirmationToken(user);

            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo("aichatahiir@gmail.com");
            mailMessage.setSubject("Confirmer votre adresse email!");
            mailMessage.setFrom("hp.appointmentapp@gmail.com");		
            mailMessage.setText("Cliquer sur le lien pour confirmer votre adresse email : "
            +"http://localhost:8181/confirm-account?token="+confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);
 			modelAndView.addObject("successMessage", "User is registered successfully!");
 		}
 		modelAndView.addObject("emp", new Employe());
 		modelAndView.setViewName("user_auth/registerService");
 		return modelAndView;
 	}
  @RequestMapping(value="/confirm-account", method= {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView confirmUserAccount(ModelAndView modelAndView, @RequestParam("token")String confirmationToken)
  {
      ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);

      if(token != null)
      {
          Utilisateur user = utilisateurRepository.findByEmail(token.getUser().getEmail());
          user.setActive(true);
          userService.addUser(user);
          modelAndView.setViewName("accountVerified");
      }
      else
      {
          modelAndView.addObject("message","The link is invalid or broken!");
          modelAndView.setViewName("error");
      }

      return modelAndView;
  }

	/*
	 * @RequestMapping("/home") public String ClientHome(ModelMap model) {
	 * List<Utilisateur> lr= iPlateformeMetier.getAllUsers(); model.put("preList",
	 * lr); List<Service> ls= iPlateformeMetier.getAllServices();
	 * model.put("serviceList", ls); List<Categorie> lc=
	 * iPlateformeMetier.getAllCat(); model.put("categorieList", lc); return
	 * "client/home"; }
	 */
	 @RequestMapping(value= {"/rdv"}, method=RequestMethod.GET)
	 public ModelAndView home() {
	  ModelAndView model = new ModelAndView();
	  Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	  Utilisateur user = userService.findUserByEmail(auth.getName());
	  List<Utilisateur> lr= iPlateformeMetier.getAllUsers();
	    model.addObject("preList", lr);
	    List<Service> ls= iPlateformeMetier.getAllServices();
	    model.addObject("serviceList", ls);
	    List<Categorie> lc= iPlateformeMetier.getAllCat();
	    model.addObject("categorieList", lc);
	  List<RendezVous> listRdvConfirmes= iPlateformeMetier.mesRdvConfirmes();
	  List<RendezVous> listRdvNonConfirmes= iPlateformeMetier.mesRdv();
	  
	  model.addObject("userName", user.getPrenom() + " " + user.getNom());
	  model.addObject("listRdvConfirmes", listRdvConfirmes);
	  model.addObject("listRdvNonConfirmes", listRdvNonConfirmes);
	  model.setViewName("client/rdv");
	  return model;
	 }
	 
	 @RequestMapping(value= {"/access_denied"}, method=RequestMethod.GET)
	 public ModelAndView accessDenied() {
	  ModelAndView model = new ModelAndView();
	  model.setViewName("403");
	  return model;
	 }
	 @RequestMapping(value = "/admin", method = RequestMethod.GET)
		public ModelAndView adminHome() {
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.setViewName("admin/home"); // resources/template/admin/home.html
			return modelAndView;
		}
	 @RequestMapping(value = "/serviceHome", method = RequestMethod.GET)
		public ModelAndView serviceHome() {
		 	
			List<RendezVous> lr= iPlateformeMetier.rdvParEmpNC();
			List<RendezVous> listRdvConfirmes= iPlateformeMetier.rdvParEmpC();
		    
			ModelAndView modelAndView = new ModelAndView();
			modelAndView.addObject("listRdv", lr);
			modelAndView.addObject("listRdvConfirmes", listRdvConfirmes);
			modelAndView.setViewName("service/home"); 
			return modelAndView;
		}
	 
}



