package com.sem.keeper.service.command

import com.sem.keeper.entity.LoanEntity
import com.sem.keeper.entity.UserEntity

data class TakeBackLoanCommand(var loanEntity: LoanEntity, var backUser: UserEntity)
