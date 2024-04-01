package com.amandeep.kotlinbasics.kotlin.basics

fun main() {
    // it copy the original object and not create the new object for the nested one, copy as reference, if original nested object got changed than copied object value get changed
    // shallow copy
    val address = AddressTest("Noida")
    val original = PersonTest("Amandeep", address)
    val copied = original.copy(name = "Tomar")
    println(original)
    println(copied)
    address.city = "Banglore"
    println(original)
    println(copied) // here copied values of address get changed

println("deep copy")
    // deep copy
    val address1 = AddressTest("Banglore")
    val original1 = PersonTest("Amandeep", address)
    val copied1 = original.clone()
    println(original1)
    println(copied1)
    original1.address.city = "New York"
    println(original1)
    println(copied1)

}

// shallow Copy
data class PersonTest(val name: String, var address: AddressTest):Cloneable{

   public override fun clone(): PersonTest {
        return PersonTest(name, address.clone())
    }

}
data class AddressTest(var city: String):Cloneable{
  public override fun clone(): AddressTest {
        return super.clone() as AddressTest
    }
}

