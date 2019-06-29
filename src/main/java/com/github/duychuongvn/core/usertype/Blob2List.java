package com.github.duychuongvn.core.usertype;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;

/**
 * Created by huynhduychuong on Oct 3, 2016.
 *
 */
@SuppressWarnings("unchecked")
public class Blob2List extends Blob2Java<List>{

    public Blob2List() {
        super(List.class);
    }


    @Override
    public Object deepCopy(Object value) throws HibernateException {
        List originalValue = (List) value;
        List<Object> deepCopiedValue = new ArrayList<>();
        deepCopiedValue.addAll(originalValue);
        return deepCopiedValue;
    }

}
