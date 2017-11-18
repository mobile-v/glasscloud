package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.CompanyEntity;

public interface CompanyRepository extends CrudRepository<CompanyEntity, Long> {

}
