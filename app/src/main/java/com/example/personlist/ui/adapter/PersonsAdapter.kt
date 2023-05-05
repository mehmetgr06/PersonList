package com.example.personlist.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.personlist.data.source.Person
import com.example.personlist.databinding.ItemPersonBinding

class PersonsAdapter :
    PagingDataAdapter<Person, PersonsAdapter.PersonViewHolder>(PersonListDiffCallback) {

    inner class PersonViewHolder(private val binding: ItemPersonBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person?) {
            binding.apply {
                textViewPerson.text = "${person?.fullName} (${person?.id})"
            }
        }
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PersonViewHolder(
        ItemPersonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

}

object PersonListDiffCallback : DiffUtil.ItemCallback<Person>() {
    override fun areItemsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: Person,
        newItem: Person
    ): Boolean {
        return oldItem == newItem
    }
}