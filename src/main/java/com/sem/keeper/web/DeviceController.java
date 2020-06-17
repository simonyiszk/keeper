package com.sem.keeper.web;

import com.sem.keeper.entity.DeviceEntity;
import com.sem.keeper.entity.UserEntity;
import com.sem.keeper.model.DeviceRegDto;
import com.sem.keeper.repo.DeviceRepository;
import com.sem.keeper.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
        return new RedirectView("/");
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
    public String edit(@ModelAttribute("newdevice") @Valid DeviceRegDto deviceRegDto,
                             BindingResult bindingResult,
                             @PathVariable("deviceid") String deviceid){
        if (bindingResult.hasErrors()){
            return "newdevice";
        }
        DeviceEntity deviceEntity=deviceRepository.findById(Long.parseLong(deviceid));
        deviceEntity.copyFromRegDto(deviceRegDto);
        deviceRepository.save(deviceEntity);
        return "redirect:/";
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
