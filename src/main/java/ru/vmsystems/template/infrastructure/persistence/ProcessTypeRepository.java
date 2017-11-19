package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.ProcessTypeEntity;

import java.util.List;

public interface ProcessTypeRepository extends CrudRepository<ProcessTypeEntity, Long> {

    List<ProcessTypeEntity> getByCompanyId(Long companyId);

    List<ProcessTypeEntity> getByCompany(CompanyEntity company);
}
