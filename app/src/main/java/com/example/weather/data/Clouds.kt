package com.example.weather.data

data class Clouds(val all: Int? = Int.MIN_VALUE){
    constructor() : this(Int.MIN_VALUE)
}
