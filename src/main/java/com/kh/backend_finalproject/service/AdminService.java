package com.kh.backend_finalproject.service;
import com.kh.backend_finalproject.dto.*;
import com.kh.backend_finalproject.entitiy.AdTb;
import com.kh.backend_finalproject.entitiy.ChatbotTb;
import com.kh.backend_finalproject.entitiy.PostTb;
import com.kh.backend_finalproject.entitiy.UserTb;
import com.kh.backend_finalproject.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final ChatbotRepository chatbotRepository ;
    private final UserRepository userRepository;
    private  final ReplyRepository replyRepository;
    private  final AdRepository adRepository;

    // 💗 전체 문의 내역 조회 (문의일 최근순 정렬)
    public List<ChatbotUserDto> findAllInquiryList() {
        List<ChatbotUserDto> chatbotUserDtos = chatbotRepository.findAllInquiryWithUserNickname();
        return chatbotUserDtos;
    }

    // 💗 전체 댓글 내역 조회 (문의일 최근순 정렬)
    public List<ReplyUserDto> findAllReplyList() {
        List<ReplyUserDto> replyUserDtos = replyRepository.findAllReplyWithUserNickname();
        return replyUserDtos;
    }

    // 💗 전체 광고 내역 조회
    public List<AdDto> findAllAdList() {
        List<AdTb> ads = adRepository.findAll();
        List<AdDto> adDtos = new ArrayList<>();
        for (AdTb ad : ads) {
            AdDto adDto = new AdDto();
            adDto.setAdNum(ad.getId());
            adDto.setName(ad.getName());
            adDto.setImgUrl(ad.getImgUrl());
            adDtos.add(adDto);
        }
        return adDtos;
    }
    // 💗 광고 추가
    public AdDto createAd(AdDto adDto) {
        AdTb adTb = new AdTb();
        adTb.setName(adDto.getName());
        adTb.setImgUrl(adDto.getImgUrl());
        adRepository.save(adTb);

        AdDto savedAdDto = new AdDto();
        savedAdDto.setAdNum(adTb.getId());
        savedAdDto.setName(adTb.getName());
        savedAdDto.setImgUrl(adTb.getImgUrl());
        return savedAdDto;
    }

}

