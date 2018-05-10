package ru.vmsystems.template.domain.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.vmsystems.template.domain.model.CompanyEntity;
import ru.vmsystems.template.domain.model.ReceptionOfOrderEntity;
import ru.vmsystems.template.domain.model.user.UserEntity;
import ru.vmsystems.template.infrastructure.persistence.CompanyRepository;
import ru.vmsystems.template.infrastructure.persistence.ReceptionOfOrderRepository;
import ru.vmsystems.template.infrastructure.persistence.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {

    @NotNull
    private final UserRepository userRepository;
    @NotNull
    private final ReceptionOfOrderRepository receptionOfOrderRepository;
    @NotNull
    private final CompanyRepository companyRepository;
    private final HttpServletRequest httpRequest;

    private Map<String, Long> receptions = new ConcurrentHashMap<>();

    @Autowired
    public SessionService(@NotNull UserRepository userRepository,
                          @NotNull ReceptionOfOrderRepository receptionOfOrderRepository,
                          @NotNull CompanyRepository companyRepository,
                          HttpServletRequest httpRequest) {
        this.userRepository = userRepository;
        this.receptionOfOrderRepository = receptionOfOrderRepository;
        this.companyRepository = companyRepository;
        this.httpRequest = httpRequest;
    }

    public void setReceptionOfOrder(Long reception) {
        Optional<UserEntity> user = userRepository.getByLogin(httpRequest.getRemoteUser());

        if (!user.isPresent()) throw new RuntimeException("Пользователь не найден");

        CompanyEntity company = companyRepository.findOne(user.get().getCompany().getId());
        receptionOfOrderRepository.getByCompanyId(company.getId()).stream()
                .filter(receptionOfOrderEntity -> reception.equals(receptionOfOrderEntity.getId()))
                .findFirst().orElseThrow(() -> new RuntimeException("Точка приема не найдена"));

        receptions.put(httpRequest.getSession().getId(), reception);
    }

    public Optional<Long> getReceptionOfOrder() {
        return Optional.ofNullable(receptions.get(httpRequest.getSession().getId()));
    }

    public String getCurrentReceptionOfOrderName() {
        Long id = receptions.get(httpRequest.getSession().getId());
        ReceptionOfOrderEntity entity = receptionOfOrderRepository.findOne(id);
        if (entity == null) {
            return "";
        }
        return entity.getName();
    }

    public Optional<ReceptionOfOrderEntity> getCurrentReceptionOfOrder() {
        Long id = receptions.get(httpRequest.getSession().getId());
        if (id == null) return Optional.empty();

        ReceptionOfOrderEntity entity = receptionOfOrderRepository.findOne(id);
        return Optional.of(entity);
    }
}
