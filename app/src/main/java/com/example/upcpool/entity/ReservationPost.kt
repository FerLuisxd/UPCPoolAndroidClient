package com.example.upcpool.entity

import java.util.*

class ReservationPost(_room : ReservationRoom, _hours : Int, _userSecondaryCode : String, _start : Date) {
    val room : ReservationRoom = _room
    val hours : Int = _hours
    val userSecondaryCode : String = _userSecondaryCode
    val start : Date = _start
}