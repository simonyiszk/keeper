package com.sem.keeper.service

import java.lang.Exception

class DeviceAlreadyOnLoanException(errorMessage: String?) : Exception(errorMessage)