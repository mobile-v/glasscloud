//package ru.vmsystems.template.infrastructure.persistence;
//
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.CrudRepository;
//import org.springframework.data.repository.query.Param;
//import ru.vmsystems.template.domain.model.tag.TagsEntity;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface TagsRepository extends CrudRepository<TagsEntity, Long> {
//    Optional<TagsEntity> getById(Integer id);
//
//    List<TagsEntity> getByTagNameAndAutoclaveOrderById(String tagName, Integer autoclave, Pageable pageable);
//    List<TagsEntity> findByAndAutoclaveOrderByDate(Integer autoclave, Pageable pageable);
//
//    @Query(value = "select e from TagsEntity e " +
//            "where e.autoclave = :autoclave " +
//            "order by e.date desc ")
//    List<TagsEntity> getTags(@Param("autoclave") Integer autoclave, Pageable pageable);
//
//    TagsEntity getTop1ByTagNameAndAutoclave(String tagName, Integer autoclave);
//    TagsEntity findTop1ByTagNameAndAutoclaveOrderByIdDesc(String tagName, Integer autoclave);
//}
