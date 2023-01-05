package com.halfa.islami.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

object Utils {

    fun inflateLayout(layoutId: Int, parent: ViewGroup): View {
        return LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
    }

    fun getProperties(obj: Any): List<Map<String, String>> {
        val maps: MutableList<Map<String, String>> = mutableListOf()

        val fields = obj.javaClass.getDeclaredFields()
        for (field in fields) {
            val key = field.name
            field.isAccessible = true
            val value = field.get(obj).toString()

            maps.add(mapOf(key to value))
        }
        return maps
    }





}