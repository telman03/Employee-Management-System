package app.service;

import app.exception.UserFoundException;
import app.model.Employee;
import app.repository.EmployeeRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class EmployeeService {
    private final EmployeeRepository repo;

    public List<Employee> getAllEmployees(){
        return new ArrayList<>((Collection) repo.findAll());
    }


    public Employee getEmployeeById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new UserFoundException("No employee record exist for given id"));


    }



    public Employee createOrUpdateEmployee(Employee entity) {
        if(entity.getId()  == null)
        {
            entity = repo.save(entity);

            return entity;
        }
        else
        {
            Optional<Employee> employee = repo.findById(entity.getId());

            if(employee.isPresent())
            {
                Employee e = employee.get();
                e.setEmail(entity.getEmail());
                e.setFirstName(entity.getFirstName());
                e.setLastName(entity.getLastName());


                return e = repo.save(e);
            } else {
                entity = repo.save(entity);

                return entity;
            }
        }

//        return repo.findById(entity.getId())
//                .map(e -> {
//                    e.setEmail(entity.getEmail());
//                    e.setFirstName(entity.getFirstName());
//                    e.setLastName(entity.getLastName());
//                    return e = repo.save(entity);
//                });
    }

    public void deleteEmployeeById(Long id) throws UserFoundException {
        Optional<Employee> employee = repo.findById(id);
        repo.findById(id)
                .ifPresentOrElse(id1 -> repo.deleteById(id1.getId()),
                        () -> { throw new UserFoundException("No employee record exist for given id"); });

    }
}
