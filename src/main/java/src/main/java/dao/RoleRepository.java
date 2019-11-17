package src.main.java.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import src.main.java.entities.Role;

@Repository("roleRepository")
public interface RoleRepository extends JpaRepository<Role, Integer> {

 Role findByRole(String role);
}