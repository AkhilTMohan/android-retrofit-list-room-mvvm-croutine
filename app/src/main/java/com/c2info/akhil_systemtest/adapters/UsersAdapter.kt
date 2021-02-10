package com.c2info.akhil_systemtest.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.c2info.akhil_systemtest.R
import com.c2info.akhil_systemtest.interfaces.CallBack
import com.c2info.akhil_systemtest.models.Data
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.item_user.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch

/** Recycler view adapter to list the images from raw file as grid */

class UsersAdapter(private val callBack: CallBack) :
    RecyclerView.Adapter<Holder>() {
    var data = arrayListOf<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return getHolder(R.layout.item_user, parent)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val itemView = holder.itemView
        val positionData = data[position]
        CoroutineScope(Main).launch {
            itemView.text?.text = String.format("%s", positionData.email)
        }
        itemView.text.setOnClickListener {
            callBack.onClick(data[holder.adapterPosition])
        }
    }

    private fun getHolder(layout: Int, parent: ViewGroup): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(layout, parent, false))
    }

    fun updateList(item: ArrayList<Data>) {
        if (!data.containsAll(item)) {
            data.addAll(item)
            notifyDataSetChanged()
        }
    }
}