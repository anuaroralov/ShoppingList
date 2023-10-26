package com.anuar.shoppinglist.representation

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.anuar.shoppinglist.R
import com.anuar.shoppinglist.databinding.ActivityMainBinding
import com.anuar.shoppinglist.domain.ShopItem
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var adapter: ShopListAdapter

    @Inject
    lateinit var viewModelFactory:ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this,viewModelFactory)[MainViewModel::class.java]
    }

    private lateinit var dialog:Dialog

    private val component by lazy {
        (application as MyApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        component.inject(this)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dialog=Dialog(this)

        val rvShopList=binding.rvShopList
        adapter=ShopListAdapter(
            {
                viewModel.changeEnableState(it)
            },
            {
                addOrChangeShopItem(CHANGE,it)
            }
        )       
        rvShopList.adapter =adapter
        rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_DISABLED,ShopListAdapter.MAX_POOL_SIZE)
        rvShopList.recycledViewPool.setMaxRecycledViews(ShopListAdapter.VIEW_TYPE_ENABLED,ShopListAdapter.MAX_POOL_SIZE)
        setupSwipeListener(rvShopList)

        viewModel.shopList.observe(this){
            adapter.submitList(it)
        }

        binding.buttonAddShopItem.setOnClickListener{
            addOrChangeShopItem(ADD,ShopItem("",0,true))
        }



    }

    private fun setupSwipeListener(rvShopList: RecyclerView) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(item)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun addOrChangeShopItem(addOrChange:Int, oldItem: ShopItem){
        dialog.setContentView(R.layout.dialog_layout)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)

        val button: Button = dialog.findViewById(R.id.save_button)
        val name: EditText = dialog.findViewById(R.id.et_name)
        val count: EditText = dialog.findViewById(R.id.et_count)

        if(addOrChange==ADD) {
            button.setOnClickListener {
                if( (count.text.toString().isBlank()) or (name.text.toString().isBlank())){
                    if(name.text.toString().isBlank()){
                        name.setError("Invalid name")
                    }
                    if(count.text.toString().isBlank()){
                        count.setError("Invalid count")
                    }
                }
                else{
                    val newItem =
                        ShopItem(
                            name.text.toString(),
                            Integer.parseInt(count.text.toString()),
                            true)
                    viewModel.addShopItem(newItem)
                    dialog.cancel()
                }
            }

        }
        else if(addOrChange== CHANGE){
            name.setText(oldItem.name)
            count.setText(oldItem.count.toString())

            button.setOnClickListener {
                if( (count.text.toString().isBlank()) or (name.text.toString().isBlank())){
                    if(name.text.toString().isBlank()){
                        name.setError("Invalid name")
                    }
                    if(count.text.toString().isBlank()){
                        count.setError("Invalid count")
                    }
                }
                else {
                    val newItem =
                        ShopItem(
                            name.text.toString(),
                            Integer.parseInt(count.text.toString()),
                            oldItem.enabled,
                            oldItem.id
                        )
                    viewModel.editShopItem(newItem)
                    dialog.cancel()
                }
            }
        }
        dialog.show()
    }

    companion object{
        const val ADD=110
        const val CHANGE=111
    }

}