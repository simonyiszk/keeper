package com.sem.keeper.service.command

import com.sem.keeper.entity.LoanRequestEntity
import com.sem.keeper.entity.UserEntity

data class AcceptLoanRequestCommand(var loanRequestEntity: LoanRequestEntity, var kiad: UserEntity)
