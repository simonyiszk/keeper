package com.sem.keeper.web

import com.sem.keeper.entity.LoanEntity
import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.DeviceRepository
import com.sem.keeper.repo.LoanRepository
import com.sem.keeper.repo.UserRepository
import com.sem.keeper.service.DeviceAlreadyOnLoanException
import com.sem.keeper.service.LoanRequestService
import com.sem.keeper.service.LoanService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes
import org.springframework.web.servlet.view.RedirectView
import java.util.*

@Controller
@RequestMapping("/loan")
class LoanController(
    private val loanRepository: LoanRepository,
    private val deviceRepository: DeviceRepository,
    private val userRepository: UserRepository,
    private val loanService: LoanService,
    private val loanRequestService: LoanRequestService
) {
    @GetMapping("/list")
    fun list(model: Model): String {
        val loans = loanRepository.findByVisszavetteIsNullOrderByTakeDate()
        model.addAttribute("loans", loans)
        return "loanlist"
    }

    @GetMapping("/list/all")
    fun listAll(model: Model): String {
        val loans = loanRepository.findAll()
        model.addAttribute("loans", loans)
        return "loanlistall"
    }

    @GetMapping("/return/{loanid}")
    fun returnLoan(
        model: Model,
        @PathVariable("loanid") loanid: Long
    ): String {
        val loanEntity = loanRepository.findById(loanid)
        model.addAttribute("loanEntity", loanEntity.get())
        return "confirmReturnLoan"
    }

    @PostMapping("/return/{loanid}")
    fun returnLoan(
        @SessionAttribute(required = false) user: UserEntity,
        @PathVariable("loanid") loanid: String,
        @ModelAttribute("loanEntity") loanEntityNote: LoanEntity,
        redirectAttributes: RedirectAttributes
    ): RedirectView {
        val loanEntity = loanRepository.findById(loanid.toLong())
        if (loanEntity.isEmpty) {
            redirectAttributes.addFlashAttribute("message", "A kölcsönzés nem található")
            return RedirectView("/loan/list")
        }
        loanService.visszahoz(loanEntity.get(), user)
        loanEntity.get().note = loanEntityNote.note
        loanRepository.save(loanEntity.get())
        return RedirectView("/loan/list")
    }

    @GetMapping("/extend/{loanid}")
    fun extendLoan(@PathVariable("loanid") loanid: String): RedirectView {
        val loanEntity = loanRepository.findById(loanid.toLong())
        loanService.hosszabbit(loanEntity.get())
        return RedirectView("/loan/list")
    }

    @GetMapping("/new/{deviceid}")
    fun newloanStepTwo(model: Model, @PathVariable("deviceid") deviceid: String): String {
        val device = deviceRepository.findById(deviceid.toLong()).get()
        model.addAttribute("device", device)
        //List<LoanEntity> loans = loanRepository.findByDeviceEntityAndVisszavetteIsNullOrderByTakeDate(device);
        return "newloanstep2"
    }

    @GetMapping("/new/{deviceid}/{userid}")
    fun newloanStepThree(
        model: Model,
        @PathVariable("deviceid") deviceid: String,
        @PathVariable("userid") userid: String
    ): String {
        val device = deviceRepository.findById(deviceid.toLong()).get()
        val tarhaUser = userRepository.findById(userid.toLong())
        model.addAttribute("device", device)
        model.addAttribute("tarhauser", tarhaUser.get())
        return "newloanstep3"
    }

    @PostMapping("/new/{deviceid}/{userid}")
    fun addNewLoanFinalStep(
        @SessionAttribute(required = false) user: UserEntity,
        @PathVariable("deviceid") deviceid: Long,
        @PathVariable("userid") userid: Long,
        redirectAttributes: RedirectAttributes,
        @RequestParam note: Optional<String>
    ): RedirectView {
        val device = deviceRepository.findById(deviceid).get()
        val tarhaUser = userRepository.findById(userid)
        try {
            loanService.newLoan(device, user, tarhaUser.get(), note)
        } catch (ex: DeviceAlreadyOnLoanException) {
            redirectAttributes.addFlashAttribute("message", "Az eszköz már ki van adva :(")
        }
        return RedirectView("/device/" + device.id)
    }

    @GetMapping("/request/{deviceid}")
    fun requestDevice(
        model: Model,
        @PathVariable("deviceid") deviceid: String
    ): String {
        val device = deviceRepository.findById(deviceid.toLong()).get()
        model.addAttribute("device", device)
        return "confirmrequest"
    }

    @GetMapping("/request/{deviceid}/ok")
    fun requestDeviceOk(
        @SessionAttribute(required = false) user: UserEntity,
        @PathVariable("deviceid") deviceid: String
    ): RedirectView {
        val device = deviceRepository.findById(deviceid.toLong()).get()
        loanRequestService.addLoanRequest(user, device)
        return RedirectView("/")
    }
}