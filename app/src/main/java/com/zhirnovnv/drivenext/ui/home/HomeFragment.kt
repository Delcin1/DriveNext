package com.zhirnovnv.drivenext.ui.home

import android.os.Bundle
import android.text.Editable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import android.text.TextWatcher
import com.zhirnovnv.drivenext.databinding.FragmentHomeBinding
import com.zhirnovnv.drivenext.activities.Car
import com.zhirnovnv.drivenext.activities.CarAdapter
import com.zhirnovnv.drivenext.R


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var carAdapter: CarAdapter
    private val carList = listOf(
        Car("S 500 Sedan", "Mercedes-Benz", "2500Р в день", "A/T", "Бензин", R.drawable.ic_mercbenz),
        Car("TT Quattro", "Audi", "2000Р в день", "DC/T", "Бензин", R.drawable.ic_audi),
        Car("Huracan", "Lamborghini", "3500Р в день", "DC/T", "Бензин", R.drawable.ic_lamborghini),
        Car("Roadster", "Tesla", "3000Р в день", "SS/T", "Электро", R.drawable.ic_tesla)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                          savedInstanceState: Bundle?): View {

        binding = FragmentHomeBinding.inflate(layoutInflater)
        val view = binding.root
        binding.rvCar.layoutManager = LinearLayoutManager(context)
        carAdapter = CarAdapter(carList)
        binding.rvCar.adapter = carAdapter

        binding.textInputEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filterCars(s.toString())
            }
        })

        return view
    }

    private fun filterCars(query: String) {
        val filteredList = if (query.isEmpty()) {
            carList
        } else {
            carList.filter { it.brand.contains(query, ignoreCase = true) }
        }
        carAdapter.updateCars(filteredList)
    }

}