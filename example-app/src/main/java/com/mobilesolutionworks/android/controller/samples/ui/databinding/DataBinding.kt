package com.mobilesolutionworks.android.controller.samples.ui.databinding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

import java.lang.ref.WeakReference

/**
 * Created by yunarta on 17/3/17.
 */

interface DataBinding {

    class ViewHolder<U, V : ViewDataBinding>(val binding: V) : RecyclerView.ViewHolder(binding.root) {

        var userObject: U? = null
    }

    abstract class Adapter<U> : RecyclerView.Adapter<ViewHolder<U, out ViewDataBinding>>() {

        private var mInflater: WeakReference<LayoutInflater>? = null

        protected abstract fun getItemLayout(viewType: Int): Int

        private fun createInflaterIfNeeded(parent: ViewGroup): LayoutInflater {
            if (mInflater == null || mInflater!!.get() == null) {
                mInflater = WeakReference(LayoutInflater.from(parent.context))
            }

            return mInflater!!.get()!!
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<U, out ViewDataBinding> {
            return ViewHolder(DataBindingUtil.inflate<ViewDataBinding>(createInflaterIfNeeded(parent), getItemLayout(viewType), parent, false))
        }
    }

    abstract class SingleTypeAdapter<U, V : ViewDataBinding> : RecyclerView.Adapter<ViewHolder<U, V>>() {

        private var mInflater: WeakReference<LayoutInflater>? = null

        protected abstract fun getItemLayout(): Int

        private fun createInflaterIfNeeded(parent: ViewGroup): LayoutInflater {
            if (mInflater == null || mInflater!!.get() == null) {
                mInflater = WeakReference(LayoutInflater.from(parent.context))
            }

            return mInflater!!.get()!!
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<U, V> {
            return ViewHolder(DataBindingUtil.inflate<V>(createInflaterIfNeeded(parent), getItemLayout(), parent, false))
        }
    }
}