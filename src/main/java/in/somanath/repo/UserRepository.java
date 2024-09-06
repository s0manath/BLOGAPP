package in.somanath.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.somanath.entity.User;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	
	boolean existsByEmail(String email);
	
	User findByEmail(String email);

}
