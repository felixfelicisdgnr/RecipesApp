package com.doganur.recipesapp.db

import androidx.room.TypeConverter
import androidx.room.TypeConverters

//Aşağıdaki kod Room veritabanında kullanılan veri türlerini dönüştürmek için bir TypeConverter sınıfıdır.

// @TypeConverters notasyonu, veritabanı sınıfı içinde kullanılan tüm TypeConverter sınıflarını belirtmek için kullanılır.
//MealTypeConverter sınıfı, Any türünden String türüne ve String türünden Any türüne dönüştürme işlemlerini gerçekleştirir.
@TypeConverters
class MealTypeConverter {

/*
 fromAnyToString fonk., Any türündeki bir nesneyi String türüne dönüştürür. attribute parametresi, dönüştürülecek nesneyi
 temsil eder. Fonk., attribute parametresinin null olup olmadığını kontrol eder ve null ise boş bir String döndürür.
 Aksi takdirde, attribute parametresini doğrudan String türüne dönüştürür.
 */

    @TypeConverter
    fun fromAnyToString(attribute:Any?) : String {
        if (attribute == null)
            return ""
        return attribute as String
    }
/*
yukarıdaki kodun tam tersini yapar,fakat dönüştürülen nesne türü belirsiz olduğundan,bu fonk. kullanım açısından güvensiz olabilir.

 */

    @TypeConverter
    fun fromStringToAny(attribute : String?) : Any {
        if (attribute == null)
            return ""
        return attribute
    }
}