package com.doganur.recipesapp

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_definition.*
import java.io.ByteArrayOutputStream


class DefinitionFragment : Fragment() {

    // startActivityForResult() yeniden düzenleyeceğim


    private var selectedBitmap: Bitmap? = null
    private var selectedUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_definition, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        saveButton.setOnClickListener {
            save(it)
        }

        selectImageImageView.setOnClickListener {
            selectImage(it)
        }

        //arguments geldi mi ona bakacağım
        arguments?.let{

            var cameInfo = DefinitionFragmentArgs.fromBundle(it).info
            if(cameInfo.equals("icomemenu")){
                // yeni bir yemek eklemeye geldi
                mealNamePlainText.setText("")
                mealDescriptionPlainText.setText("")
                saveButton.visibility= View.VISIBLE

                //fragmenttaki tarifim nasılsa birebir aynısının görünmesini sağlamış olabilrim
                val selectedImageBackground = BitmapFactory.decodeResource(context?.resources,R.drawable.selectfoodimage)
                selectImageImageView.setImageBitmap(selectedImageBackground)

            }else{
                //kullanici eski bir şey gösterecekse onu nasıl yapıyor onu yapacağız
                //daha önce oluşturulan yemeği eklemeye geldi
                //buttonun görünümünü değiştireceğim çünkü bir şey kaydetmeyecek görünmesine gerek yok
                saveButton.visibility= View.INVISIBLE
            //eski bir veri olacağı için bir id göndermesi gerekiyor bana o id yi alcağım, fragment arguments içinde id tanımlamıştım

                val selectedId = DefinitionFragmentArgs.fromBundle(it).id

                context?.let{

                    try {

                        val database = it.openOrCreateDatabase("foods", Context.MODE_PRIVATE, null )
                        val cursor = database.rawQuery("SELECT * FROM meals WHERE id = ?", arrayOf(selectedId.toString()))

                        val mealNameIndex = cursor.getColumnIndex("mealsname")
                        val mealIngredientsIndex = cursor.getColumnIndex("mealsingredients")
                        val mealsImage = cursor.getColumnIndex("image")

                        while (cursor.moveToNext()){
                            mealNamePlainText.setText(cursor.getString(mealNameIndex))
                            mealDescriptionPlainText.setText(cursor.getString(mealIngredientsIndex))

                            val newByteArray = cursor.getBlob(mealsImage)
                            val newBitmap = BitmapFactory.decodeByteArray(newByteArray,0,newByteArray.size)
                            selectImageImageView.setImageBitmap(newBitmap)
                        }


                    } catch (e: Exception){
                        e.printStackTrace()
                    }


                }

            }
        }
    }

     private fun save(view: View) {
        //SQLite kaydetmek
        val mealName = mealNamePlainText.text.toString()
        val mealDescription = mealDescriptionPlainText.text.toString()

        try {
            context?.let {
                if(selectedUri != null){
                    if (Build.VERSION.SDK_INT >= 28){
                        val source = ImageDecoder.createSource(it.contentResolver,selectedUri!!)
                        selectedBitmap = ImageDecoder.decodeBitmap(source)
                        selectImageImageView.setImageBitmap(selectedBitmap)
                    }else{
                        selectedBitmap = MediaStore.Images.Media.getBitmap(it.contentResolver,selectedUri)
                        selectImageImageView.setImageBitmap(selectedBitmap)
                    }
                }
            }

        }catch (e: Exception){
            e.printStackTrace()
        }

        //seçilen bitmap null değilse başlamak istiyorum işlemlerime
        if (selectedBitmap != null) {
            val smallBitmap = convertBitmaptoSmaller(selectedBitmap!!, 300)
            println(selectedBitmap)
            val outputStream = ByteArrayOutputStream()
            smallBitmap.compress(Bitmap.CompressFormat.PNG,50,outputStream)
            val byteArrays= outputStream.toByteArray()


            try {
                context?.let{
                    val database = it.openOrCreateDatabase("foods", Context.MODE_PRIVATE, null )
                    database.execSQL("CREATE TABLE IF NOT EXISTS meals (id INTEGER PRIMARY KEY, mealsname VARCHAR, mealsingredients VARCHAR, image BLOB)")

                    val sqlString = "INSERT INTO meals (mealsname, mealsingredients, image) VALUES (?,?,?)"
                    val statement = database.compileStatement(sqlString)
                    statement.bindString(1,mealName)
                    statement.bindString(2,mealDescription)
                    statement.bindBlob(3,byteArrays)
                    statement.execute()
                }

            } catch (e: Exception){
                e.printStackTrace()
            }

            val action = DefinitionFragmentDirections.actionDefinitionFragmentToListFragment()
            Navigation.findNavController(view).navigate(action)

        }
    }

    private fun selectImage(view: View) {
        requestMultiplePermissions.launch(READ_EXTERNAL_STORAGE)
        //SQL kaydedeceğimiz verilerin 1MB'dan büyük olması sorun yaratır, biz fotoğrafın boyutunu bilemeyiz görselin küçük boyutlu olduğundan emin
        // olmak için daha küçük boyutlu bitmap haline getirmemiz lazım bundan emin olmak için de bir şeyler yazalım

    }

    //izin isteme fonksiyonları
    private val requestMultiplePermissions = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = "image/*"
                startGalleryForResultLauncher.launch(intent)
            } else {
                println("reddedildi")
            }
        }

    private val startGalleryForResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { data ->

        selectedUri = data.data?.data
        selectedUri?.let {
            selectImageImageView.setImageURI(selectedUri)
        }

    }

    // fonksiyon küçük bitmap'e döndürecek
    private fun convertBitmaptoSmaller(selectedUserBitmap: Bitmap, maxSize: Int): Bitmap {

        var width = selectedUserBitmap.width
        var height = selectedUserBitmap.height

        val bitmapRatio: Double = width.toDouble() / height.toDouble()

        if (bitmapRatio > 1) {
            //görselimiz yatay
            width = maxSize
            val smallerHeight = width / bitmapRatio
            height = smallerHeight.toInt()

        } else {
            //görselimiz dikey
            height = maxSize
            val smallerWidth = height * bitmapRatio
            width = smallerWidth.toInt()
        }

        return Bitmap.createScaledBitmap(selectedUserBitmap, width, height, true)
    }


}





