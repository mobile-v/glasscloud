package ru.vmsystems.template.domain.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.domain.shared.ReceptionOfOrderTransformer;
import ru.vmsystems.template.infrastructure.persistence.CompanyRepository;
import ru.vmsystems.template.infrastructure.persistence.ReceptionOfOrderRepository;
import ru.vmsystems.template.interfaces.dto.ReceptionOfOrderDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ReceptionOfOrderService {

    @NotNull
    private final ReceptionOfOrderRepository receptionOfOrderRepository;
    @NotNull
    private final CompanyRepository companyRepository;

    @Autowired
    public ReceptionOfOrderService(@NotNull ReceptionOfOrderRepository receptionOfOrderRepository,
                                   @NotNull CompanyRepository companyRepository) {
        this.receptionOfOrderRepository = receptionOfOrderRepository;
        this.companyRepository = companyRepository;
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

}
