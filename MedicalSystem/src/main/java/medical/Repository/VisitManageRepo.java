package medical.Repository;

import medical.Model.Patient;
import medical.Model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisitManageRepo extends CrudRepository<Visit,Long> {

}
