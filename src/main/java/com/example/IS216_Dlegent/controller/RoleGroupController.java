package com.example.IS216_Dlegent.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.IS216_Dlegent.model.RoleGroup;
import com.example.IS216_Dlegent.service.AccountService;

@Controller
public class RoleGroupController {
    
    @Autowired
    private AccountService accountService;

    Logger logger = LoggerFactory.getLogger(getClass());

    @GetMapping("/role/{username}")
    public String getRoleView(@PathVariable String username, Model model) {
        List<RoleGroup> roleGroups = accountService.getRoleGroupsByUsername(username);
        logger.info("Test List: {}", roleGroups);
        logger.info("Test List: Role Groups for user '{}':", username);
        for (RoleGroup roleGroup : roleGroups) {
            logger.info(" - Group ID: {}, Group Name: {}", roleGroup.getId(), roleGroup.getGroupName());
        }
        model.addAttribute("roleGroups", roleGroups);
        model.addAttribute("username", username);
        return "MyRole";
    }
}
