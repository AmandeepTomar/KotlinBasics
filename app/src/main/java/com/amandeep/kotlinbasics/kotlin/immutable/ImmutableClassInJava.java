package com.amandeep.kotlinbasics.kotlin.immutable;



//


final public class ImmutableClassInJava {
    private final String name;
    private final String address;
    private final int code;


    public ImmutableClassInJava(String name, String address, int code) {
        this.name = name;
        this.address = address;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String toString() {
        return "ImmutableClassInJava{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", code=" + code +
                '}';
    }
}





