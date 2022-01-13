package com.amandeep.kotlinbasics.kotlin.immutable;

public class ImmutableClassWithMutableClass {
    final private String name;
    final private String age;
    final private Address address;

    public ImmutableClassWithMutableClass(String name, String age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public Address getAddress() {
        return new Address(address);
    }

    @Override
    public String toString() {
        return "ImmutableClassWithMutableClass{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", address=" + address +
                '}';
    }
}
