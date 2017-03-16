package com.mobilesolutionworks.android.controller.samples.ui.databinding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.lang.ref.WeakReference;

/**
 * Created by yunarta on 17/3/17.
 */

public class DataBinding {

    public static class ViewHolder<U, V extends ViewDataBinding> extends RecyclerView.ViewHolder {

        private final V mBinding;

        private U mUserObject;

        public ViewHolder(V binding) {
            super(binding.getRoot());
            mBinding = binding;
        }

        public final V getBinding() {
            return mBinding;
        }

        public final U getUserObject() {
            return mUserObject;
        }

        public final void setUserObject(U userObject) {
            mUserObject = userObject;
        }
    }

    public static abstract class Adapter<U> extends RecyclerView.Adapter<ViewHolder<U, ? extends ViewDataBinding>> {

        private WeakReference<LayoutInflater> mInflater;

        protected abstract int getItemLayout(int viewType);

        private LayoutInflater createInflaterIfNeeded(ViewGroup parent) {
            if (mInflater == null || mInflater.get() == null) {
                mInflater = new WeakReference<>(LayoutInflater.from(parent.getContext()));
            }

            return mInflater.get();
        }

        @Override
        public ViewHolder<U, ? extends ViewDataBinding> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder<>(DataBindingUtil.inflate(createInflaterIfNeeded(parent), getItemLayout(viewType), parent, false));
        }
    }

    public static abstract class SingleTypeAdapter<U, V extends ViewDataBinding> extends RecyclerView.Adapter<ViewHolder<U, V>> {

        private WeakReference<LayoutInflater> mInflater;

        protected abstract int getItemLayout();

        private LayoutInflater createInflaterIfNeeded(ViewGroup parent) {
            if (mInflater == null || mInflater.get() == null) {
                mInflater = new WeakReference<>(LayoutInflater.from(parent.getContext()));
            }

            return mInflater.get();
        }

        @Override
        public ViewHolder<U, V> onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder<>(DataBindingUtil.inflate(createInflaterIfNeeded(parent), getItemLayout(), parent, false));
        }
    }
}