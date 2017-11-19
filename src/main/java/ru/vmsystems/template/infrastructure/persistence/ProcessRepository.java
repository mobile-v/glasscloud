package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.ProcessEntity;

import java.util.List;

public interface ProcessRepository extends CrudRepository<ProcessEntity, Long> {

    List<ProcessEntity> getByCompanyId(Long companyId);

    List<ProcessEntity> getByCompany(CompanyEntity company);
}
