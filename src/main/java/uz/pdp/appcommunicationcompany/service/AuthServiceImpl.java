package uz.pdp.appcommunicationcompany.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.appcommunicationcompany.dto.LoginDto;
import uz.pdp.appcommunicationcompany.dto.PassportDto;
import uz.pdp.appcommunicationcompany.dto.RegisterDto;
import uz.pdp.appcommunicationcompany.entity.*;
import uz.pdp.appcommunicationcompany.entity.enums.RoleName;
import uz.pdp.appcommunicationcompany.repository.CompanyRepository;
import uz.pdp.appcommunicationcompany.repository.PassportRepository;
import uz.pdp.appcommunicationcompany.repository.RoleRepository;
import uz.pdp.appcommunicationcompany.repository.UserRepository;
import uz.pdp.appcommunicationcompany.security.JwtProvider;

import java.util.*;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

    final UserRepository userRepository;
    final PasswordEncoder passwordEncoder;
    final RoleRepository roleRepository;
    final AuthenticationManager authenticationManager;
    final JwtProvider jwtProvider;
    final JavaMailSender javaMailSender;
    final PassportRepository passportRepository;
    final CompanyRepository companyRepository;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider,
                           JavaMailSender javaMailSender,
                           PassportRepository passportRepository,
                           CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.javaMailSender = javaMailSender;
        this.passportRepository = passportRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public Response register(RegisterDto registerDto) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal().equals("anonymousUser")) {
            User user = saveTempUserData(registerDto);
            if (user == null)
                return new Response("There was an error with dto!", false);

            userRepository.save(user);
            return new Response(RoleName.HEAD_OF_COMPANY.name() + " added!", true);
        } else {

            User userContext = (User) authentication.getPrincipal();
            Set<Role> roles = userContext.getRoles();

            for (Role role : roles) {
                User user1 = saveTempUserData(registerDto);
                if (user1 == null)
                    return new Response("There was an error with dto!", false);

                Set<Role> roles1 = user1.getRoles();
                for (Role role1 : roles1) {
                    if (role1 != null) {
                        if (role.getLevel() < role1.getLevel()) {

                            // Agar EMPLOYEE qo'shilayotgan bo'lsa, unga xodim uchun takrorlanmas username beriladi
                            String generatedUsername = "";
                            if (role1.getRoleName().equals(RoleName.EMPLOYEE)) {
                                generatedUsername = generateUsername(registerDto.getFirstname(), registerDto.getLastname());
                                user1.setUsername(generatedUsername);
                            }

                            userRepository.save(user1);
                            if (!"".equals(generatedUsername)) {
                                sendEmail(user1.getEmail(), generatedUsername);
                                return new Response(role1.getRoleName().name() + " added! Generated username sent to employee email!", true, generatedUsername);
                            }

                            return new Response(role1.getRoleName().name() + " added!", true);
                        } else {
                            return new Response("Sorry! You can not add a user with this role!", false);
                        }

                    } else {
                        return new Response("Role id is empty!", false);
                    }
                }
            }

        }

        return null;
    }

    @Override
    public Response login(LoginDto loginDto) {

        try {
            String username = "";
            if (loginDto.getEmail() != null)
                username = loginDto.getEmail();
            if (loginDto.getUsername() != null)
                username = loginDto.getUsername();

            Authentication authenticate =
                    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, loginDto.getPassword()));

            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(username, user.getRoles());
            return new Response("Token", true, token);

        } catch (BadCredentialsException badCredentialsException) {
            return new Response("Username or password failed!", false);
        }
    }

    public User saveTempUserData(RegisterDto registerDto) {
        User user = new User();
        user.setFirstname(registerDto.getFirstname());
        user.setLastname(registerDto.getLastname());
        user.setEmail(registerDto.getEmail());
        user.setEnabled(true);
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        Set<Integer> roleIdList = registerDto.getRoleIdList();
        Set<Role> roleSet = new HashSet<>();

        for (Integer roleId : roleIdList) {
            Optional<Role> optionalRole = roleRepository.findById(roleId);
            optionalRole.ifPresent(roleSet::add);
        }
        user.setRoles(roleSet);
        user.setEmailCode(UUID.randomUUID().toString());

        PassportDto passportDto = registerDto.getPassportDto();
        Passport passport = new Passport();
        passport.setNumber(passportDto.getNumber());
        passport.setDateOfIssue(passportDto.getDateOfIssue());
        passport.setDateOfExpire(passportDto.getDateOfExpire());
        passport.setGivenBy(passportDto.getGivenBy());

        user.setPassport(passport);

        passport.setUser(user);

        if(registerDto.getCompanyId() != null) {
            Optional<Company> optionalCompany = companyRepository.findById(registerDto.getCompanyId());
            if (!optionalCompany.isPresent())
                return null;
            user.setCompany(optionalCompany.get());
        }


        return user;
    }

    public String generateUsername(String firstname, String lastname) {
        Random generator = new Random();
        int randomNumber = generator.nextInt(9000) + 10;
        String username = (firstname.charAt(new Random().nextInt(firstname.length())) + "$").toUpperCase() + lastname.substring(0, lastname.length() - 2) + randomNumber;

        return username;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser;
        if (!username.contains("@")) {
            optionalUser = userRepository.findByUsername(username);
        } else {
            optionalUser = userRepository.findByEmail(username);
        }
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException(username + " not found!"));
    }

    public void sendEmail(String sendingEmail, String username) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("gm.khamza@gmail.com");
            mailMessage.setTo(sendingEmail);
            mailMessage.setSubject("Your username for enter to system!");
            mailMessage.setText("USERNAME: " + username);

            javaMailSender.send(mailMessage);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
