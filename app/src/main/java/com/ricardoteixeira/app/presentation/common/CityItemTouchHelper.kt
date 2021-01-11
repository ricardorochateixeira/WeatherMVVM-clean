package com.ricardoteixeira.app.presentation.common

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class CityItemTouchHelperCallback(private val itemTouchHelperAdapter: CityItemTouchHelperAdapter): ItemTouchHelper.Callback() {
    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        return if (viewHolder.itemView.isFocusable == false) {
            0
        } else {
            makeMovementFlags(
                ItemTouchHelper.ACTION_STATE_IDLE,
                ItemTouchHelper.START or ItemTouchHelper.END
            )
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        itemTouchHelperAdapter.onItemSwiped(viewHolder, viewHolder.adapterPosition)
    }
}

interface CityItemTouchHelperAdapter {
    fun onItemSwiped(viewHolder: RecyclerView.ViewHolder, position:Int)
}