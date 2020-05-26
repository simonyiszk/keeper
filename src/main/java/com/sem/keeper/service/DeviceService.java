package com.sem.keeper.service;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.model.DeviceRegDto;
import com.sem.keeper.repo.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class DeviceService {

    @Autowired
    DeviceRepository repo;

    public DeviceEntity addDevice(DeviceRegDto deviceRegDto){
        DeviceEntity toAdd = new DeviceEntity(deviceRegDto);
        return repo.save(toAdd);
    }

    public void deleteDevice(DeviceEntity deviceEntity){
        repo.delete(deviceEntity);
    }
}
