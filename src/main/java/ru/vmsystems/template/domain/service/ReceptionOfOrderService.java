package ru.vmsystems.template.domain.service;

import org.dozer.Mapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.domain.shared.ReceptionOfOrderTransformer;
import ru.vmsystems.template.infrastructure.persistence.CompanyRepository;
import ru.vmsystems.template.infrastructure.persistence.ReceptionOfOrderRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;
import ru.vmsystems.template.interfaces.dto.ReceptionOfOrderDto;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ReceptionOfOrderService extends BackService {

    @NotNull
    private final ReceptionOfOrderRepository receptionOfOrderRepository;
    @NotNull
    private final CompanyRepository companyRepository;
    @NotNull
    private final UserRepository userRepository;
    @NotNull
    private final Mapper mapper;

    private Map<String, Long> receptions = new ConcurrentHashMap<>();

    @Autowired
    public ReceptionOfOrderService(@NotNull ReceptionOfOrderRepository receptionOfOrderRepository,
                                   @NotNull CompanyRepository companyRepository,
                                   @NotNull UserRepository userRepository,
                                   @NotNull Mapper mapper) {
        this.receptionOfOrderRepository = receptionOfOrderRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public void setReceptionOfOrder(Long reception) {
        Optional<UserEntity> user = userRepository.getByLogin(getLogin());

        if (!user.isPresent()) throw new RuntimeException("Пользователь не найден");

        CompanyEntity company = companyRepository.findOne(user.get().getCompany().getId());
        receptionOfOrderRepository.getByCompanyId(company.getId()).stream()
                .filter(receptionOfOrderEntity -> reception.equals(receptionOfOrderEntity.getId()))
                .findFirst().orElseThrow(() -> new RuntimeException("Точка приема не найдена"));

        receptions.put(getSesionId(), reception);
    }

    public Optional<Long> getReceptionOfOrder() {
        return Optional.ofNullable(receptions.get(getSesionId()));
    }

    public Optional<String> getReceptionOfOrderName() {
        Long id = receptions.get(getSesionId());
        ReceptionOfOrderEntity entity = receptionOfOrderRepository.findOne(id);
        if (entity == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(entity.getName());
    }

    public List<ReceptionOfOrderDto> get() {
        List<ReceptionOfOrderDto> result = new ArrayList<>();
        receptionOfOrderRepository.findAll()
                .forEach(client -> result.add(ReceptionOfOrderTransformer.toDto(client)));
        return result;
    }

    public Optional<ReceptionOfOrderDto> get(@NotNull Long orderId) {
        ReceptionOfOrderEntity order = receptionOfOrderRepository.findOne(orderId);
        if (order == null) {
            return Optional.empty();
        }

        ReceptionOfOrderDto result = ReceptionOfOrderTransformer.toDto(order);

        return Optional.of(result);
    }

    public void save(@NotNull ReceptionOfOrderDto receptionOfOrderDto) {
        CompanyEntity company = companyRepository.findOne(receptionOfOrderDto.getCompany().getId());

        ReceptionOfOrderEntity entity = receptionOfOrderRepository.save(ReceptionOfOrderTransformer.toEntity(receptionOfOrderDto, company));
        receptionOfOrderDto.setId(entity.getId());
    }

    public void delete(Long orderId) {
        receptionOfOrderRepository.delete(orderId);
    }

    public List<ReceptionOfOrderDto> getByUserName(String userName) {
        Optional<UserEntity> user = userRepository.getByLogin(userName);

        if (!user.isPresent()) return Collections.emptyList();

        CompanyEntity company = companyRepository.findOne(user.get().getCompany().getId());
        return receptionOfOrderRepository.getByCompanyId(company.getId()).stream()
                .map(receptionOfOrder -> mapper.map(receptionOfOrder, ReceptionOfOrderDto.class))
                .collect(Collectors.toList());
    }
}
