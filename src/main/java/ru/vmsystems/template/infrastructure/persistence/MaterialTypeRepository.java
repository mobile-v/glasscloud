package ru.vmsystems.template.infrastructure.persistence;


import org.springframework.data.repository.CrudRepository;
import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.MaterialTypeEntity;

import java.util.List;

public interface MaterialTypeRepository extends CrudRepository<MaterialTypeEntity, Long> {

    List<MaterialTypeEntity> getByCompanyId(Long companyId);

    List<MaterialTypeEntity> getByCompany(CompanyEntity company);
}
