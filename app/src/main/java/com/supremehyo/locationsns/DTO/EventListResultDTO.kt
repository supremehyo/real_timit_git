package com.supremehyo.locationsns.DTO

data class EventListResultDTO(
    var count : Int,
    var next : String,
    var previous : String,
    var results : List<EventDTO>

)
