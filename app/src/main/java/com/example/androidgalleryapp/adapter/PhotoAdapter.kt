package com.example.androidgalleryapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.androidgalleryapp.R
import com.example.app_data.PhotoDao

internal abstract class PhotoAdapter(
        private val list: List<PhotoDao>
) : RecyclerView.Adapter<PhotoAdapter.RecyclerViewHolder>() {

    internal inner class  RecyclerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        internal val image: ImageView = view.findViewById<View>(R.id.image) as ImageView
        internal val title: TextView = view.findViewById<View>(R.id.title) as TextView
        internal val path: TextView = view.findViewById<View>(R.id.path) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val inflate: View = LayoutInflater.from(parent.context).inflate(R.layout.photo_adapter, parent, false)
        val viewHolder = RecyclerViewHolder(inflate)
        viewHolder.image.setOnClickListener {
            val pos = viewHolder.adapterPosition
            val photoDao = list[pos]
            onRecyclerViewClicked(photoDao)
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.image.setImageBitmap(list[position].image)
        holder.title.text = list[position].title
        holder.path.text = list[position].path
    }

    override fun getItemCount(): Int {
        return list.size
    }

    abstract fun onRecyclerViewClicked(photoDao: PhotoDao?)
}