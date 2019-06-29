package com.github.duychuongvn.core.usertype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.github.duychuongvn.exception.TechnicalException;

public class ObjectConvertor {

    public static byte[] convertToBytes(Object object) {
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        ObjectOutputStream objectStream;
        try {
            objectStream = new ObjectOutputStream(byteStream);
            objectStream.writeObject(object);
            return byteStream.toByteArray();
        } catch (IOException e) {
            throw new TechnicalException(e);
        }

    }

    public static Object convertToObject(byte[] bytes) {
        ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectStream;
        try {
            objectStream = new ObjectInputStream(byteStream);
            return objectStream.readObject();
        } catch (IOException e) {
            throw new TechnicalException(e);
        } catch (ClassNotFoundException e) {
            throw new TechnicalException(e);
        }

    }

}
