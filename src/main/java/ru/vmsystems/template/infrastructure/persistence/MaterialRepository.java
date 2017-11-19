package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.MaterialEntity;

import java.util.List;

public interface MaterialRepository extends CrudRepository<MaterialEntity, Long> {

    List<MaterialEntity> getByCompanyId(Long companyId);

    List<MaterialEntity> getByCompany(CompanyEntity company);
}
