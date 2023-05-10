package com.doganur.recipesapp.db

import android.content.Context
import androidx.room.*
import com.doganur.recipesapp.pojo.Meal

//veri tabanı sınıfı olduğunu belirttik, veritabanının sürümünü de aynı zamanda belirttik
@Database(entities = [Meal::class], version = 1)
//RoomDatabase() den kalıtım aldığını belirttik
@TypeConverters(MealTypeConverter::class)
abstract class MealDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    //veritabanının tek bir örneğini oluşturmak için companion object kullandım
    companion object {
        //MealDatabase'in örneğini tutuyor, Volatile ile değişkeni her zaman senkronize edebiliyoruz.
        @Volatile
        var INSTANCE: MealDatabase? = null

        //Synchronized fonk. sadece bir iş parçacığının getInstance fonksiyonunu aynı anda çağırmasını sağlar. (?)
        @Synchronized
        fun getInstance(context: Context): MealDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    MealDatabase::class.java,
                    "meal.db"
                ).fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as MealDatabase
            /*
    getInstance fonksiyonu, MealDatabase sınıfının tek bir örneğini oluşturur veya mevcut bir örneği döndürür.
    Bu sayede, uygulama genelinde tek bir veritabanı örneği oluşturulması sağlanır. databaseBuilder fonksiyonu,
    veritabanı örneğinin oluşturulmasını sağlar. fallbackToDestructiveMigration fonksiyonu, veritabanı şemasında bir değişiklik olduğunda,
    verilerin korunmasını sağlamak için kullanılır. Ancak, fallbackToDestructiveMigration kullanıldığında, mevcut verilerin silinmesi gerekebilir.
                        */
        }
    }
}