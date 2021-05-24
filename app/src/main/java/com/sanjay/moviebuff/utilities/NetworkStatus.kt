package com.sanjay.moviebuff.utilities

sealed class NetworkStatus {
    object Success : NetworkStatus()
    object Loading : NetworkStatus()
    object Error : NetworkStatus()
}
