package forex.copytradingforex.service.impl;

import forex.copytradingforex.model.entity.RoleEntity;
import forex.copytradingforex.model.entity.enums.RoleEnum;
import forex.copytradingforex.model.view.RoleViewModel;
import forex.copytradingforex.repository.RoleRepository;
import forex.copytradingforex.service.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<RoleViewModel> getTraderAndInvestorRoles() {
        List<RoleViewModel> roles = roleRepository.findAll()
                .stream()
                .filter(role -> !role.getRole().equals(RoleEnum.MASTER))
                .map(role-> modelMapper.map(role,RoleViewModel.class))
                .collect(Collectors.toList());

        return roles;
    }
}
