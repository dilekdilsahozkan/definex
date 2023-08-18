package com.example.definexcase.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.definexcase.databinding.ItemEmptyListBinding

class BaseAdapter<Dto, Binding : ViewDataBinding>(
    private val layoutId: Int,
    private val modelId: Int? = null,
    private val isSameDto: ((oldItem: Dto, newItem: Dto) -> Boolean)? = null
    ) :
    ListAdapter<Dto, BaseAdapter.ViewHolder<Binding>>(object :
        DiffUtil.ItemCallback<Dto>() {
        override fun areItemsTheSame(
            oldItem: Dto,
            newItem: Dto
        ): Boolean {
            return isSameDto?.invoke(oldItem, newItem) ?: false
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(
            oldItem: Dto,
            newItem: Dto
        ): Boolean {
            return oldItem == newItem
        }
    }) {

    private val EMPTY_TYPE = 1
    private val ENTITY_TYPE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        EMPTY_TYPE -> ViewHolder.EmptyViewHolder(
            ItemEmptyListBinding.inflate(LayoutInflater.from(parent.context),parent, false),
            this
        )
        else -> ViewHolder.EntityViewHolder<Binding>(
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), layoutId, parent, false),
            this
        )
    }

    override fun onBindViewHolder(holder: ViewHolder<Binding>, position: Int) {
        val item = getItem(position)

        when (holder) {
            is ViewHolder.EntityViewHolder<*> -> {
                modelId?.let {
                    if (item is SimpleDto<*>) {
                        holder.binding.setVariable(it, item.value)
                    } else {
                        holder.binding.setVariable(it, item)
                    }
                }
            }
            else -> {}
        }

    }

    override fun submitList(list: List<Dto>?) {

        if (list != null && list.isEmpty()) {
            super.submitList(listOf(null))
        } else {
            super.submitList(list)
        }
    }

    sealed class ViewHolder<Binding : ViewDataBinding>(binding: Binding, listAdapter: BaseAdapter<*, *>) :
        RecyclerView.ViewHolder(binding.root) {
        class EntityViewHolder<Binding : ViewDataBinding>(
            val binding: Binding,
            listAdapter: BaseAdapter<*, *>
        ) : ViewHolder<Binding>(binding, listAdapter)

        class EmptyViewHolder<Binding : ViewDataBinding>(
            val binding: ItemEmptyListBinding,
            listAdapter: BaseAdapter<*, *>
        ) :
            ViewHolder<Binding>(binding as Binding, listAdapter)
    }

    override fun getItemViewType(position: Int): Int {

        if (getItem(position) == null) {
            return EMPTY_TYPE
        }

        return ENTITY_TYPE
    }
}