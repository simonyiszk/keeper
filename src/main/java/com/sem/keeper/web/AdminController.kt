package com.sem.keeper.web

import com.sem.keeper.repo.UserRepository
import com.sem.keeper.service.GoodMusicService
import com.sem.keeper.service.UserService
import com.sem.keeper.service.command.DeleteUserCommand
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.view.RedirectView

@Controller
@RequestMapping("/admin")
class AdminController(
    private val goodMusicService: GoodMusicService,
    private val userRepository: UserRepository,
    private val userService: UserService
) {
    @GetMapping("/deleteuser")
    @Deprecated("Why did I do this?")
    fun deleteUser(@RequestParam useremail: String): RedirectView {
        val user = userRepository.findByEmail(useremail)
        if (user != null) {
            userService.deleteUser(DeleteUserCommand(user))
        }
        return RedirectView("/admin/users")
    }

    @GetMapping("/deleteUserById")
    fun deleteUser(@RequestParam userId: Long): RedirectView {
        val user = userRepository.findById(userId)
        user.ifPresent { userService.deleteUser(DeleteUserCommand(it)) }
        return RedirectView("/admin/users")
    }

    @GetMapping("/users")
    fun listUsers(model: Model): String {
        model.addAttribute("users", userRepository.findAll())
        return "userlist"
    }

    @GetMapping("")
    fun root(model: Model): String {
        model.addAttribute("musicUrl", goodMusicService.nextGoodMusic!!.url)
        return "admin"
    }
}