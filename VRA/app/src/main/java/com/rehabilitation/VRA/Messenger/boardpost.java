package com.rehabilitation.VRA.Messenger;

import java.util.List;

/**
 * Created by Darren on 28/06/2015.
 */
public class boardpost {

    Type_SMS post;
    List<Type_SMS> responses;

    public boardpost(Type_SMS post, List<Type_SMS> responses){
        this.post = post;
        this.responses = responses;
    }

    public Type_SMS getpost(){return post;}
    public List<Type_SMS> getresponses(){
        return responses;
    }

}
