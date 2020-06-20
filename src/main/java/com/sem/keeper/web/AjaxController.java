package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.repo.DeviceRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Service
@RestController
@RequestMapping("/ajax")
public class AjaxController {

    @Autowired
    DeviceRepository devices;

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
}

