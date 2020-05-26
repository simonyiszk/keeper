package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.model.DeviceRegDto;
import com.sem.keeper.repo.DeviceRepository;
import com.sem.keeper.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    DeviceService deviceService;

    @Autowired
    DeviceRepository deviceRepository;

    @GetMapping("/delete/{deviceid}")
    public RedirectView delete(@PathVariable("deviceid") String deviceid){
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));

        deviceService.deleteDevice(device);
        return new RedirectView("/device/list");
    }

    @GetMapping("/edit/{deviceid}")
    public String edit(Model model, HttpSession session, @PathVariable("deviceid") String deviceid){
        DeviceEntity device = deviceRepository.findById(Long.parseLong(deviceid));

        UserEntity user = (UserEntity) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("newdevice",device);
        model.addAttribute("backUrl","/device/edit/"+deviceid);
        return "newdevice";
    }

    @PostMapping("/edit/{deviceid}")
    public RedirectView edit(@ModelAttribute("newdevice") @Valid DeviceRegDto deviceRegDto,
                             @PathVariable("deviceid") String deviceid){
        DeviceEntity deviceEntity=deviceRepository.findById(Long.parseLong(deviceid));
        deviceEntity.copyFromRegDto(deviceRegDto);
        deviceRepository.save(deviceEntity);
        return new RedirectView("/device/list");
    }

    @GetMapping("/list")
    public String listdevices(Model model, HttpSession session){
        UserEntity user = (UserEntity) session.getAttribute("user");
        model.addAttribute("user",user);
        model.addAttribute("devices", deviceRepository.findAll());
        return "devicemgmt";
    }

    @GetMapping("/new")
    public String showRegistrationForm(WebRequest request, Model model) {
        DeviceRegDto deviceRegDto = new DeviceRegDto();
        model.addAttribute("newdevice", deviceRegDto);
        model.addAttribute("backUrl","/device/new");
        return "newdevice";
    }

    @PostMapping("/new")
    public RedirectView newDevice(
            @ModelAttribute("newdevice") @Valid DeviceRegDto deviceRegDto,
            HttpServletRequest request, Errors errors) {
        deviceService.addDevice(deviceRegDto);
        return new RedirectView("/device/list");
    }

}
