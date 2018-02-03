package ru.vmsystems.template.domain.service;

import org.dozer.Mapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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

    public List<ReceptionOfOrderDto> get() {
        List<ReceptionOfOrderDto> result = new ArrayList<>();
        receptionOfOrderRepository.getByCompanyId(getCompanyId())
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

    @NotNull
    public ReceptionOfOrderDto create(@NotNull ReceptionOfOrderDto dto) {
        return update(null, dto);
    }

    public ReceptionOfOrderDto update(@Nullable Long id, @NotNull ReceptionOfOrderDto receptionOfOrderDto) {
        receptionOfOrderDto.setId(id);
        CompanyEntity company = companyRepository.findOne(receptionOfOrderDto.getCompany().getId());

        ReceptionOfOrderEntity entity = receptionOfOrderRepository.save(ReceptionOfOrderTransformer.toEntity(receptionOfOrderDto, company));
        receptionOfOrderDto.setId(entity.getId());

        return mapper.map(entity, ReceptionOfOrderDto.class);
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
