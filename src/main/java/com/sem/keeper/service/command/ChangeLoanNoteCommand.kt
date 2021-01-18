package com.sem.keeper.service.command

import com.sem.keeper.entity.LoanEntity

class ChangeLoanNoteCommand(val loanEntity: LoanEntity, val note: String)
