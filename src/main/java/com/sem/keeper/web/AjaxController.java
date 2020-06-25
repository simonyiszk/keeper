package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.LoanEntity;
import com.sem.keeper.entity.LoanRequestEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.repo.DeviceRepository;
import com.sem.keeper.repo.LoanRepository;
import com.sem.keeper.repo.LoanRequestRepository;
import com.sem.keeper.repo.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


@Service
@RestController
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    DeviceRepository devices;

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    LoanRequestRepository loanRequestRepository;

    @GetMapping("/myReqs")
    public Iterable<LoanRequestEntity> myReqs(HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");
        return loanRequestRepository.findByElvinne(user);
    }

    @GetMapping("/devices")
    public Iterable<DeviceEntity> devicePag(@RequestParam int page, @RequestParam int pageSize){
        Pageable pageable = PageRequest.of(page, pageSize);
        return devices.findAll(pageable);
    }

    @GetMapping(value = {"/devicesearch","/devicesearch/{pageNumber}","/devicesearch/{pageNumber}/{pageSize}"})
    public Page<DeviceEntity> searchDevice(@RequestParam String term,
                                           @PathVariable Optional<Integer> pageNumber,
                                           @PathVariable Optional<Integer> pageSize){
        Integer page = pageNumber.orElse(0);
        var pageable = PageRequest.of(page,pageSize.orElse(10));
        return devices.findByNameContainingIgnoreCase(term, pageable);
    }

    @GetMapping(value = {"/loanlist","/loanlist/{pageNumber}/{pageSize}"})
    public Page<LoanEntity> loanlist(HttpSession session,
                                     @RequestParam Optional<String> filter,
                                     @PathVariable Optional<Integer> pageNumber,
                                     @PathVariable Optional<Integer> pageSize){
        Pageable pageable = PageRequest.of(pageNumber.orElse(0),pageSize.orElse(10));
        if (filter.isEmpty() || filter.get().equals("all")) {
            return loanRepository.findAll(pageable);
        } else {
            switch (filter.get()){
                case "mine":{
                    UserEntity user = (UserEntity) session.getAttribute("user");
                    return loanRepository.findByElvitte(user, pageable);
                }
                case "out":{
                    return loanRepository.findByVisszavetteIsNullOrderByTakeDate(pageable);
                }
                default:{
                    return loanRepository.findAll(pageable);
                }
            }
        }
    }
}

