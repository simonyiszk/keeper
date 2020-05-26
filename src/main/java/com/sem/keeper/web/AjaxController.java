package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.repo.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

    @GetMapping("/devicesearch")
    public List<DeviceEntity> searchDevice(@RequestParam String term){
        return devices.findByNameContainingIgnoreCase(term);
    }
}

