package com.sem.keeper.web

import com.sem.keeper.command.NewDeviceCommand
import com.sem.keeper.repo.DeviceRepository
import com.sem.keeper.repo.LoanRepository
import com.sem.keeper.service.DeviceService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.validation.Errors
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.view.RedirectView
import javax.validation.Valid

@Controller
@RequestMapping("/device")
class DeviceController(
    private val deviceService: DeviceService,
    private val deviceRepository: DeviceRepository,
    private val loanRepository: LoanRepository
)
{
    @GetMapping("/delete/{deviceid}")
    fun delete(@PathVariable("deviceid") deviceid: String): RedirectView {
        val device = deviceRepository.findById(deviceid.toLong()).get()
        deviceService.deleteDevice(device)
        return RedirectView("/")
    }

    @GetMapping("/edit/{deviceid}")
    fun edit(model: Model, @PathVariable("deviceid") deviceid: String): String {
        val device = deviceRepository.findById(deviceid.toLong()).get()
        model.addAttribute("newdevice", device)
        model.addAttribute("backUrl", "/device/edit/$deviceid")
        return "newdevice"
    }

    @PostMapping("/edit/{deviceid}")
    fun edit(
        @ModelAttribute("newdevice") deviceRegDto: @Valid NewDeviceCommand,
        bindingResult: BindingResult,
        @PathVariable("deviceid") deviceid: String
    ): String {
        if (bindingResult.hasErrors()) {
            return "newdevice"
        }
        val deviceEntity = deviceRepository.findById(deviceid.toLong()).get()
        deviceEntity.copyFromRegDto(deviceRegDto)
        deviceRepository.save(deviceEntity)
        return "redirect:/"
    }

    @GetMapping("/new")
    fun showRegistrationForm(model: Model): String {
        val deviceRegDto = NewDeviceCommand()
        model.addAttribute("newdevice", deviceRegDto)
        model.addAttribute("backUrl", "/device/new")
        return "newdevice"
    }

    @PostMapping("/new")
    fun newDevice(
        @ModelAttribute("newdevice") deviceRegDto: @Valid NewDeviceCommand,
        errors: Errors?
    ): RedirectView {
        val deviceEntity = deviceService.addDevice(deviceRegDto)
        return RedirectView("/device/" + deviceEntity.id)
    }

    @GetMapping("{deviceid}")
    fun profile(model: Model, @PathVariable("deviceid") deviceid: Long): String {
        val deviceEntity = deviceRepository.findById(deviceid).get()
        model.addAttribute("device", deviceEntity)
        val lastLoan = loanRepository.findFirstByDeviceEntityOrderByTakeDateDesc(deviceEntity)
        if (lastLoan != null) model.addAttribute("lastloanid", lastLoan.id)
        return "deviceprofile"
    }
}