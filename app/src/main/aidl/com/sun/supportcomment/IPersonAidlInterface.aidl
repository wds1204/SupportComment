// IPersonAidlInterface.aidl
package com.sun.supportcomment;

// Declare any non-default types here with import statements

interface IPersonAidlInterface {

    //具体的业务
    void setName(String name);
    void setAge(int age);
    String getInfo();
}
