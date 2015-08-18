package com.rehabilitation.VRA.Messenger;

import java.io.Serializable;
import java.util.List;


public class Type_Boardpost implements Serializable {

    Type_SMS post;
    List<Type_SMS> responses;

    public Type_Boardpost(Type_SMS post, List<Type_SMS> responses){
        this.post = post;
        this.responses = responses;
    }

    public Type_SMS getpost(){return post;}
    public List<Type_SMS> getresponses(){
        return responses;
    }

}
