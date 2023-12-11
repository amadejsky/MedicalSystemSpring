package medical.Repository;

import medical.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserManageRepo extends JpaRepository<User,Long> {

}
