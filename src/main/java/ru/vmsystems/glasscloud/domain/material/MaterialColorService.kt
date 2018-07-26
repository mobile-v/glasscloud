//package ru.vmsystems.glasscloud.domain.material
//
//import org.dozer.DozerBeanMapper
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.stereotype.Service
//import ru.vmsystems.glasscloud.domain.BackService
//import ru.vmsystems.template.interfaces.dto.MaterialColorDto
//import java.util.Optional
//import java.util.stream.Collectors
//
//@Service
//class MaterialColorService @Autowired
//constructor(private val repository: MaterialColorRepository) : BackService() {
//
//    fun get(): List<MaterialColorDto> {
//        return repository.getByCompanyId(getCompanyId()).stream()
//                .map<Any> { entity -> mapper.map(entity, MaterialColorDto::class.java) }
//                .collect<List<MaterialColorDto>, Any>(Collectors.toList())
//    }
//
//    operator fun get(id: Long): Optional<MaterialColorDto> {
//        val entity = repository.findOne(id) ?: return Optional.empty<MaterialColorDto>()
//
//        return Optional.of(mapper.map(entity, MaterialColorDto::class.java))
//    }
//
//    fun create(dto: MaterialColorDto): MaterialColorDto {
//        return update(null, dto)
//    }
//
//    fun update(id: Long?, dto: MaterialColorDto): MaterialColorDto {
//
//        dto.setId(id)
//        var entity = mapper.map(dto, MaterialColorEntity::class.java)
//        entity.setCompany(getCompany())
//        entity = repository.save<MaterialColorEntity>(entity)
//
//        return mapper.map(entity, MaterialColorDto::class.java)
//    }
//
//    fun delete(orderId: Long?) {
//        repository.delete(orderId)
//    }
//}
