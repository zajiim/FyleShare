package com.fyndr.fileshare.presentation.send_or_receive

sealed class SendOrReceiveEvents {
    object OnReceiverClick : SendOrReceiveEvents()
}