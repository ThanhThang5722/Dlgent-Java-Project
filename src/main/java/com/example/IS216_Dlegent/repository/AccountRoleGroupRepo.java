package com.example.IS216_Dlegent.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.IS216_Dlegent.model.AccountRoleGroup;

@Repository
public class AccountRoleGroupRepo{

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Phương thức để thêm dữ liệu vào bảng ACCOUNT_ROLE_GROUP
    public void saveAccountRoleGroup(AccountRoleGroup accountRoleGroup) {
        String sql = "INSERT INTO ACCOUNT_ROLE_GROUP (ACCOUNT_ID, ROLE_GROUP_ID) VALUES (?, ?)";

        try {
            jdbcTemplate.update(sql, accountRoleGroup.getAccountId(), accountRoleGroup.getRoleGroupId());  // Thực thi câu lệnh INSERT
        } catch (Exception e) {
            e.printStackTrace();  // Xử lý ngoại lệ nếu có lỗi xảy ra
        }
    }
}
