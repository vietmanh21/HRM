package com.loizenai.jwtauthentication.helper;


import com.loizenai.jwtauthentication.exception.DuplicatedException;
import com.loizenai.jwtauthentication.model.Role;
import com.loizenai.jwtauthentication.model.RoleName;
import com.loizenai.jwtauthentication.model.User;
import com.loizenai.jwtauthentication.repository.RoleRepository;
import com.loizenai.jwtauthentication.repository.UserRepository;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReadExcelFile {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public List<User> readFile(File file) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileInputStream);
        Sheet datatypeSheet = workbook.getSheetAt(0);
        DataFormatter formatter = new DataFormatter();

        Iterator<Row> iterator = datatypeSheet.iterator();
        Row firstRow = iterator.next();
        Cell firstCell = firstRow.getCell(0);
        List<User> users = new ArrayList<>();

        while(iterator.hasNext()) {
            Row curRow = iterator.next();
            String username = formatter.formatCellValue(curRow.getCell(0));
            System.out.println("DEBUGG:  " + username);
            if (userRepository.existsByUsername(username)) {
                throw new DuplicatedException("Username already exist");
            }
            String password = encoder.encode(formatter.formatCellValue(curRow.getCell(1)));
            System.out.println("DEBUGG2:  " + password);


            // Giả sử vai trò được lưu trữ dưới dạng chuỗi phân tách bằng dấu phẩy
            String rolesString = formatter.formatCellValue(curRow.getCell(2));
            System.out.println(rolesString);
            Set<String> strRoles = Arrays.stream(rolesString.split(",")).collect(Collectors.toSet());

            Set<Role> roles = new HashSet<>();

            strRoles.forEach(role -> {
                switch (role) {
                    case "manager":
                        Role managerRole = roleRepository.findByName(RoleName.ROLE_MANAGER)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                        roles.add(managerRole);

                        break;
                    case "developer":
                        Role devRole = roleRepository.findByName(RoleName.ROLE_DEVELOPER)
                                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
                        roles.add(devRole);

                        break;
                }
            });
            // Định dạng ngày tháng từ file Excel
            String validUtilString = formatter.formatCellValue(curRow.getCell(3));
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate validUtil = LocalDate.parse(validUtilString, dateFormatter);

            User user = new User(username, password, roles, validUtil);
            users.add(user);
        }
        userRepository.saveAll(users);
        return users;
    }
}
