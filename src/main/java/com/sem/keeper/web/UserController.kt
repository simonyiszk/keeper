package com.sem.keeper.web

import com.sem.keeper.entity.LoanEntity
import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.LoanRepository
import com.sem.keeper.repo.LoanRequestRepository
import com.sem.keeper.repo.UserRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.validation.BindingResult
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.function.Consumer
import kotlin.collections.ArrayList

@Controller
@RequestMapping("/user")
class UserController(
    private val loanRequestRepository: LoanRequestRepository,
    private val loanRepository: LoanRepository,
    private val userRepository: UserRepository
) {
    @ResponseBody
    @GetMapping("/me")
    fun me(@SessionAttribute user: Optional<UserEntity>): UserEntity? {
        return if (user.isEmpty) null else userRepository.save(user.get())
    }

    @ResponseBody
    @GetMapping("/myRequests")
    fun myRequests(@SessionAttribute(required = false) user: UserEntity): Set<LoanRequestEntity> {
        return loanRequestRepository.findByElvinne(user)
    }

    @ResponseBody
    @GetMapping("/myRequestIds")
    fun myRequestIds(@SessionAttribute user: Optional<UserEntity>): Collection<Long?> {
        val res = ArrayList<Long?>()
        user.ifPresent {
            val set = loanRequestRepository.findByElvinne(it)
            set.forEach(Consumer { (_, deviceEntity) -> res.add(deviceEntity!!.id) })
        }
        return res
    }

    @ResponseBody
    @GetMapping("/myLoans")
    fun myLoans(@SessionAttribute(required = false) user: UserEntity): List<LoanEntity> {
        return loanRepository.findByElvitte(user)
    }

    @GetMapping("/editnote/{userid}")
    fun editnote(model: Model, @PathVariable("userid") userid: Long): String {
        val toEdit = userRepository.findById(userid)
        if (toEdit.isEmpty) {
            return "redirect:/"
        }
        model.addAttribute("toEdit", toEdit.get())
        model.addAttribute("backUrl", "/user/editnote/" + toEdit.get().id)
        return "noteedit"
    }

    @PostMapping("/editnote/{userid}")
    fun editnotesave(
        @ModelAttribute("toEdit") toEdit: UserEntity,
        bindingResult: BindingResult?,
        @PathVariable("userid") userid: Long
    ): String {
        val toEditDb = userRepository.findById(userid)
        if (toEditDb.isEmpty) {
            return "redirect:/"
        }
        val toEditValid = toEditDb.get()
        toEditValid.note = toEdit.note
        userRepository.save(toEditValid)
        return "redirect:/admin/users"
    }
}