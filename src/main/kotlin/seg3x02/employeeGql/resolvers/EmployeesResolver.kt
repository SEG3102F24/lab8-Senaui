package seg3x02.employeeGql.resolvers

import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.stereotype.Controller
import seg3x02.employeeGql.entity.Employee
import seg3x02.employeeGql.repository.EmployeesRepository
import seg3x02.employeeGql.resolvers.types.CreateEmployeeInput
import java.util.UUID
@Controller
class EmployeesResolver(private val employeesRepository: EmployeesRepository) {

    @QueryMapping
    fun employees(): List<Employee> = employeesRepository.findAll()

    @QueryMapping
    fun employeeById(id: String): Employee? = employeesRepository.findById(id).orElse(null)

    @MutationMapping
    fun newEmployee(@Argument input: CreateEmployeeInput): Employee {
        val employee = Employee(
            name = input.name ?: throw IllegalArgumentException("Name is required"),
            dateOfBirth = input.dateOfBirth ?: throw IllegalArgumentException("Date of Birth is required"),
            city = input.city ?: throw IllegalArgumentException("City is required"),
            salary = input.salary ?: throw IllegalArgumentException("Salary is required"),
            gender = input.gender,
            email = input.email,
        )
        employee.id = UUID.randomUUID().toString()
        return employeesRepository.save(employee)
    }

    // Additional mutations for deleteEmployee and updateEmployee
}
