package com.stimednp.mvvmkotlinsample.ui.util

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)