package com.shkiper.vkcupproducts.ui.group

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.shkiper.vkcupproducts.R
import com.shkiper.vkcupproducts.models.Product
import com.shkiper.vkcupproducts.network.VKProductsRequest
import com.shkiper.vkcupproducts.ui.adapters.ProductsAdapter
import com.shkiper.vkcupproducts.ui.main.MainActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import kotlinx.android.synthetic.main.fragment_group.*


class GroupFragment : Fragment() {

    companion object {
        const val GROUP_ID = "GROUP_ID"
        const val GROUP_TITLE = "GROUP_TITLE"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        initViews()
        VK.execute(VKProductsRequest(requireArguments().getString(GROUP_ID, "-1")), object: VKApiCallback<List<Product>>{
            override fun success(result: List<Product>) {
                initProductsRecyclerView(result)
            }

            override fun fail(error: Exception) {
                if(error.message == "Access denied: no access to this group | by [market.get]"){
                    tv_no_access.visibility = View.VISIBLE
                    rv_list_of_products.visibility = View.GONE
                }
                else{
                    throw java.lang.Exception(error.message)
                }

            }
        })


    }


    private fun initProductsRecyclerView(products : List<Product>) {
        with(rv_list_of_products){
            layoutManager = GridLayoutManager(requireContext(),2);
            adapter = ProductsAdapter(products)
        }

    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity?)!!.setToolbarTitle("Товары сообщества ${arguments?.getString(GROUP_TITLE, "")}")
        (activity as MainActivity?)!!.enableNavigationIcon()
        (activity as MainActivity?)!!.disableDropDownIcon()
    }

}