package com.m.edmsbackend.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import com.m.edmsbackend.dao.IAnnouncementRepository;
import com.m.edmsbackend.dto.AnnouncementDto;
import com.m.edmsbackend.exception.LoginException;
import com.m.edmsbackend.exception.ServerErrorException;
import com.m.edmsbackend.model.Announcement;
import com.m.edmsbackend.utils.DataUtils;
import com.m.edmsbackend.utils.JwtUtils;
import com.m.edmsbackend.utils.RedisUtils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

@Service
public class AnnouncementService {
    @Resource
    private IAnnouncementRepository announcementRepository;

    public AnnouncementDto addAnnouncement(AnnouncementDto newAnnouncement) {
        Announcement announcement = new Announcement();
        DataUtils.copyProperties(newAnnouncement, announcement);
        Announcement announcementRs = announcementRepository.save(announcement);
        Long announcementId = announcementRs.getId();
        newAnnouncement.setId(announcementId);
        return newAnnouncement;
    }

    public AnnouncementDto findAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findAnnouncementById(announcementId);
        if (announcement == null) {
            return new AnnouncementDto();
        }
        AnnouncementDto announcementDto = new AnnouncementDto();
        DataUtils.copyProperties(announcement, announcementDto);
        return announcementDto;
    }

    public List<AnnouncementDto> findAnnouncements(List<Integer> typeList) {
        List<AnnouncementDto> announcementDtos = new ArrayList<>();
        List<Announcement> announcements = announcementRepository.findAnnouncementsByTypeList(typeList);
        for (Announcement announcement : announcements) {
            AnnouncementDto announcementDto = new AnnouncementDto();
            DataUtils.copyProperties(announcement, announcementDto);
            announcementDtos.add(announcementDto);
        }
        return announcementDtos;
    }
}
