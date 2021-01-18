package com.sem.keeper.web

import com.sem.keeper.service.command.ChangeLoanNoteCommand
import com.sem.keeper.entity.DeviceEntity
import com.sem.keeper.entity.LoanEntity
import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.entity.UserEntity
import com.sem.keeper.repo.DeviceRepository
import com.sem.keeper.repo.LoanRepository
import com.sem.keeper.repo.LoanRequestRepository
import com.sem.keeper.repo.UserRepository
import com.sem.keeper.service.LoanService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.stream.Collectors
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

@Service
@RestController
@RequestMapping("/ajax")
class AjaxController(
    private val devices: DeviceRepository,
    private val loanRepository: LoanRepository,
    private val loanService: LoanService,
    private val userRepository: UserRepository,
    private val loanRequestRepository: LoanRequestRepository
) {
    @GetMapping("/myReqs")
    fun myReqs(@SessionAttribute user: UserEntity): Iterable<LoanRequestEntity> {
        return loanRequestRepository.findByElvinne(user)
    }

    @GetMapping("/devices")
    fun devicePag(@RequestParam page: Int, @RequestParam pageSize: Int): Iterable<DeviceEntity> {
        val pageable: Pageable = PageRequest.of(page, pageSize)
        return devices.findAll(pageable)
    }

    @GetMapping(value = ["/devicesearch", "/devicesearch/{pageNumber}", "/devicesearch/{pageNumber}/{pageSize}"])
    fun searchDevice(
        @RequestParam term: String?,
        @PathVariable pageNumber: Optional<Int>,
        @PathVariable pageSize: Optional<Int>
    ): Page<DeviceEntity?> {
        val page = pageNumber.orElse(0)
        val pageable = PageRequest.of(page!!, pageSize.orElse(10)!!)
        return devices.findByNameContainingIgnoreCase(term, pageable)
    }

    @GetMapping("/usersearch/{pageNumber}/{pageSize}")
    fun searchUser(
        @RequestParam term: String?,
        @PathVariable pageNumber: Optional<Int>,
        @PathVariable pageSize: Optional<Int>
    ): Page<UserEntity> {
        val page = pageNumber.orElse(0)
        val pageable = PageRequest.of(page!!, pageSize.orElse(10)!!)
        return userRepository.findByFullNameContainingIgnoreCase(term!!, pageable)
    }

    @GetMapping(value = ["/loanlist", "/loanlist/{pageNumber}/{pageSize}"])
    fun loanlist(
        session: HttpSession,
        @RequestParam filter: Optional<String>,
        @PathVariable pageNumber: Optional<Int>,
        @PathVariable pageSize: Optional<Int>
    ): Page<LoanEntity> {
        val pageable: Pageable = PageRequest.of(
            pageNumber.orElse(0),
            pageSize.orElse(10)
        )
        return if (filter.isEmpty || filter.get() == "all") {
            loanRepository.findAll(pageable)
        } else {
            when (filter.get()) {
                "mine" -> {
                    val user = session.getAttribute("user") as UserEntity
                    loanRepository.findByElvitte(user, pageable)
                }
                "out" -> {
                    loanRepository.findByVisszavetteIsNullOrderByTakeDate(pageable)
                }
                else -> {
                    loanRepository.findAll(pageable)
                }
            }
        }
    }

    @GetMapping("/outdevices")
    fun outdevices(): Collection<Long?> {
        return loanRepository.findByVisszavetteIsNullOrderByTakeDate().stream()
            .map { (_, deviceEntity) -> deviceEntity!!.id }.collect(Collectors.toSet())
    }

    @PostMapping("/loanNote")
    fun loanNoteEdit(@RequestBody requestBody: Map<String, String>, response: HttpServletResponse): String {
        val loanEntity = loanRepository.findById(requestBody["num"]!!.toLong())
        if (loanEntity.isEmpty) {
            response.status = HttpServletResponse.SC_BAD_REQUEST
            return "Nein"
        }
        requestBody["resStr"]?.let {
            loanService.editNote(ChangeLoanNoteCommand(loanEntity.get(), it))
            return "Alma"
        }
        return "Nein"
    }
}
