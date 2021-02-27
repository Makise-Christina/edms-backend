package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.IDepartmentRepository;
import com.m.edmsbackend.dto.DepartmentDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.Department;
import com.m.edmsbackend.utils.DataUtils;
import com.m.edmsbackend.utils.JwtUtils;
import com.m.edmsbackend.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class DepartmentService {
    @Resource
    private IDepartmentRepository departmentRepository;

    public DepartmentDto findDepartment(Long id) {
        Department department = departmentRepository.findDepartmentById(id);
        DepartmentDto departmentDto = new DepartmentDto();
        DataUtils.copyProperties(department, departmentDto);
        return departmentDto;
    }

    public List<DepartmentDto> findDepartments() {
        List<DepartmentDto> departmentDtos = new ArrayList<>();
        List<Department> departments = departmentRepository.findAll();
        for (Department department : departments) {
            DepartmentDto departmentDto = new DepartmentDto();
            DataUtils.copyProperties(department, departmentDto);
            departmentDtos.add(departmentDto);
        }
        return departmentDtos;
    }
}
