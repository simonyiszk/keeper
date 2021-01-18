package com.sem.keeper.service.command

import com.sem.keeper.entity.LoanRequestEntity

sealed class DenyLoanRequestCommand {
    data class DenyLoanRequestByIdCommand(var id: Long): DenyLoanRequestCommand()
    data class DenyLoanRequestByEntityCommand(var loanRequestEntity: LoanRequestEntity): DenyLoanRequestCommand()
    companion object{
        fun of(id: Long): DenyLoanRequestByIdCommand{
            return DenyLoanRequestByIdCommand(id)
        }

        fun of(entity: LoanRequestEntity): DenyLoanRequestByEntityCommand{
            return DenyLoanRequestByEntityCommand(entity)
        }
    }
}