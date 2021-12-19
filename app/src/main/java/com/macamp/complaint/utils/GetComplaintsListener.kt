package com.macamp.complaint.utils

import com.macamp.complaint.data.model.Complaints

interface GetComplaintsListener {
    fun onSelectedItems(list: ArrayList<Complaints>)
}