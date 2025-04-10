package com.zhirnovnv.drivenext.activities

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import com.zhirnovnv.drivenext.databinding.ActivityLoaderBinding

import android.view.LayoutInflater
import android.view.ViewGroup


class CarAdapter(private var cars: List<Car>) : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {


    class CarViewHolder(private val binding: ActivityLoaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(car: Car) {
            binding.ivCar.setImageResource(car.imageResId)
            binding.tvModel.text = car.model
            binding.tvName.text = car.brand
            binding.tvPrice.text = car.price
            binding.tvTrans.text = car.transmission
            binding.tvFuel.text = car.fuel

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {

        val binding = ActivityLoaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.bind(car) // Привязываем данные к элементам через binding
    }

    override fun getItemCount(): Int = cars.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateCars(newCars: List<Car>) {
        cars = newCars
        notifyDataSetChanged()
    }
}