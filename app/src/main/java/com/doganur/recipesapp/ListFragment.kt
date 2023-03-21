
package com.doganur.recipesapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_list.*

class ListFragment : Fragment() {

    var mealNameArray = ArrayList<String> ()
    var mealIdArray = ArrayList<Int> ()

    private lateinit var listAdapter: RecyclerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listAdapter = RecyclerListAdapter(mealNameArray,mealIdArray)
        idRVCourses.layoutManager = LinearLayoutManager(context)
        idRVCourses.adapter = listAdapter //adaptera erişebileyimki yeni verilerin geldiğini söyleyebileyim

        sqlGetData()

    }

    private fun sqlGetData(){

        try {
            activity?.let {
                val database = it.openOrCreateDatabase("foods", Context.MODE_PRIVATE,null)
                val cursor = database.rawQuery("SELECT * FROM meals ", null )
                val mealNameIndex = cursor.getColumnIndex("mealsname")
                val mealIdIndex = cursor.getColumnIndex("id")

                mealNameArray.clear()
                mealIdArray.clear()

                while (cursor.moveToNext()){
                    mealNameArray.add(cursor.getString(mealNameIndex))
                    mealIdArray.add(cursor.getInt(mealIdIndex))

                }
                cursor.close()

                //yeni veriler geldi onları göster demek istiyorum, yeni veri geldiğini listemize söyleyecek ve yeni veri
                //geldi diye listemize söyleyip recyclerviewı güncelleyecek
                listAdapter.notifyDataSetChanged()

            }

        } catch (e : Exception ) {
                e.printStackTrace()
        }

    }


}