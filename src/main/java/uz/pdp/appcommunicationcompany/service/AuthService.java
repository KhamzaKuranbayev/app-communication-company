package uz.pdp.appcommunicationcompany.service;

import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.LoginDto;
import uz.pdp.appcommunicationcompany.dto.RegisterDto;
import uz.pdp.appcommunicationcompany.entity.Response;

@Service
public interface AuthService {

    Response register(RegisterDto registerDto);

    Response login(LoginDto loginDto);
}
