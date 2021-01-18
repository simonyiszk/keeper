package com.sem.keeper.service.result

import com.sem.keeper.entity.LoanEntity

sealed class NewLoanResult {
    data class NewLoanSuccess(val loanEntity: LoanEntity): NewLoanResult()
    data class NewLoanFailure(val reason: String?): NewLoanResult()
}