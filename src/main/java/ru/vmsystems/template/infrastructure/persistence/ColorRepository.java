package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.MaterialColorEntity;

import java.util.List;

public interface ColorRepository extends CrudRepository<MaterialColorEntity, Long> {

    List<MaterialColorEntity> getByCompanyId(Long companyId);
    List<MaterialColorEntity> getByCompany(CompanyEntity company);
}
