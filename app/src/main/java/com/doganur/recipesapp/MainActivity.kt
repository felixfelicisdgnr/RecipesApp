package com.doganur.recipesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.add_meal,menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /*
        herhangi bir ıtem seçilirse ne yapacağız onu burada yapıyoruz
        birden fazla item olabilir bu yüzden item kontrol etme alışkanlığı kazanmalıyım, if ile kontrol etmeliyim
        tıklanan yerin id sini kontrol ediyoruz,genelde optionsItemlarda birden fazla seçenek olabiliyor

        ->yemek tariflerinin olduğu fragmenta gideceğiz yani definition fragmenta gideceğiz,

         */
        if (item.itemId == R.id.add_meal_item){
            val action = ListFragmentDirections.actionListFragmentToDefinitionFragment("icomemenu",0)
            // ListFragmentDirections bize hazır olarak verilen fonksiyon, devamı da aksiyonu seçtiğimizde hazır oluşturulan obje
            Navigation.findNavController(this,R.id.fragmentContainerView).navigate(action)
        //Navigationı çağırdım, findNavControllerın içinde activityde olduğumu belirttim sonra görünümün id sıni istedi
        //mainactivity içerisindeki navHost fragmentın id sini verdim, .navigate diyerek (istediğimiz yere gidebilirdik ama biz actiona gitmek istedik)
        // nereye gideceğimzii belirttim
        }

        return super.onOptionsItemSelected(item)
    }
}