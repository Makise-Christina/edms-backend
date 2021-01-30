package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.IElderRepository;
import com.m.edmsbackend.dto.ElderDto;
import com.m.edmsbackend.dao.IContactRepository;
import com.m.edmsbackend.dto.ContactDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.Elder;
import com.m.edmsbackend.model.Contact;
import com.m.edmsbackend.utils.DataUtils;
import com.m.edmsbackend.utils.JwtUtils;
import com.m.edmsbackend.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class ElderService {
    @Resource
    private IElderRepository elderRepository;
    @Resource
    private IContactRepository contactRepository;

    public ElderDto addElder(ElderDto elder) {
        Elder dataIn = new Elder();
        DataUtils.copyProperties(elder, dataIn);
        Elder dataRs = elderRepository.save(dataIn);
        ElderDto result = new ElderDto();
        DataUtils.copyProperties(dataRs, result);

        // add contact
        Long elderId = result.getId();
        for (ContactDto contactDto : elder.getContactList()) {
            Contact contact = new Contact();
            DataUtils.copyProperties(contactDto, contact);
            contact.setElderId(elderId);
            contactRepository.save(contact);
        }

        result.setContactList(elder.getContactList());
        return result;
    }

    public List<ElderDto> getElders(
        String name,
        Integer gender,
        Integer nursingLevel,
        Integer startAge,
        Integer endAge,
        Integer offset,
        Integer limit
    ) {

        List<Elder> dataRs = new ArrayList<>();
        if (name != null) {
            if (startAge != null) {
                if (endAge != null) {
                    dataRs = elderRepository.findEldersByNameAndAge(name, startAge, endAge);
                }
                else {
                    dataRs = elderRepository.findEldersByNameAndStartAge(name, startAge);
                }
            } else {
                if (endAge != null) {
                    dataRs = elderRepository.findEldersByNameAndEndAge(name, endAge);
                }
                else {
                    dataRs = elderRepository.findEldersByName(name);
                }
            }
        } else {
            if (startAge != null) {
                if (endAge != null) {
                    dataRs = elderRepository.findEldersByAge(startAge, endAge);
                }
                else {
                    dataRs = elderRepository.findEldersByStartAge(startAge);
                }
            } else {
                if (endAge != null) {
                    dataRs = elderRepository.findEldersByEndAge(endAge);
                }
                else {
                    dataRs = elderRepository.findElders();
                }
            }
        }
        List<ElderDto> result = new ArrayList<>();
        Integer passed = 0;
        Integer count = 0;
        for (Elder elder : dataRs){
            ElderDto elderDto = new ElderDto();
            DataUtils.copyProperties(elder, elderDto);

            if (gender != null && elder.getGender() != gender) {
                continue;
            }

            if (nursingLevel != null && elder.getNursingLevel() != nursingLevel) {
                continue;
            }

            if (offset != null && passed < offset) {
                passed = passed + 1;
                continue;
            }

            if (limit != null && count >= limit){
                break;
            }

            //add contact list
            List<ContactDto> contactList = new ArrayList<>();
            List<Contact> contacts = contactRepository.findContactsByElderId(elderDto.getId());
            for (Contact contact : contacts) {
                ContactDto contactDto = new ContactDto();
                DataUtils.copyProperties(contact, contactDto);
                contactList.add(contactDto);
            }
            elderDto.setContactList(contactList);
            result.add(elderDto);
            count = count + 1;
        }
        return result;
    }

    public ElderDto updateElder(Long elderId, ElderDto elderDto) {
        Elder elder = elderRepository.findElderById(elderId);
        DataUtils.copyProperties(elderDto, elder);
        elder.setId(elderId);
        elderRepository.save(elder);

        // modify contact
        // delete all contacts
        contactRepository.deleteContactsByElderId(elderId);
        // add contact
        for (ContactDto contactDto : elderDto.getContactList()) {
            Contact contact = new Contact();
            DataUtils.copyProperties(contactDto, contact);
            contact.setElderId(elderId);
            contactRepository.save(contact);
        }

        return elderDto;
    }

    public void removeElder(Long elderId) {
        // delete all contacts
        contactRepository.deleteContactsByElderId(elderId);
        elderRepository.deleteById(elderId);
    }

    public List<ElderDto> getEldersByBuildingId(Long buildingId)
    {
        List<ElderDto> result = new ArrayList<>();
        List<Elder> dataRs = elderRepository.findEldersByBuildingId(buildingId);
        for (Elder elder : dataRs){
            ElderDto elderDto = new ElderDto();
            DataUtils.copyProperties(elder, elderDto);

            //add contact list
            List<ContactDto> contactList = new ArrayList<>();
            List<Contact> contacts = contactRepository.findContactsByElderId(elderDto.getId());
            for (Contact contact : contacts) {
                ContactDto contactDto = new ContactDto();
                DataUtils.copyProperties(contact, contactDto);
                contactList.add(contactDto);
            }
            elderDto.setContactList(contactList);
            result.add(elderDto);
        }
        return result;
    }
}
