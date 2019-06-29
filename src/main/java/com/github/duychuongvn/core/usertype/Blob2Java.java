package com.github.duychuongvn.core.usertype;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Objects;

public abstract class Blob2Java<T> implements UserType {

    private Class<T> className;

    public Blob2Java(Class<T> className) {
        this.className = className;
    }

    @Override
    public Object assemble(Serializable cached, Object arg1) throws HibernateException {
        return deepCopy(cached);
    }

    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    @Override
    public boolean equals(Object arg0, Object arg1) throws HibernateException {
        return Objects.equals(arg0, arg1);
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
     */
    @Override
    public int hashCode(Object owner) throws HibernateException {
        return owner.hashCode();
    }

    /*
     * (non-Javadoc)
     *
     * @see org.hibernate.usertype.UserType#isMutable()
     */
    @Override
    public boolean isMutable() {
        return true;
    }

    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException, SQLException {
        InputStream binaryStream = rs.getBinaryStream(names[0]);

        if (binaryStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int binary;
            while ((binary = binaryStream.read()) != -1) {
                byteArrayOutputStream.write(binary);
            }
        } catch (IOException e) {
            throw new HibernateException(e);
        }
        byte[] binary = byteArrayOutputStream.toByteArray();
        if (binary == null) {
            return null;
        }
        return ObjectConvertor.convertToObject(binary);
    }

    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index, SharedSessionContractImplementor sharedSessionContractImplementor) throws HibernateException, SQLException {
        T attributes = null;
        if (value != null) {
            attributes = (T) value;
            try {
                st.setBytes(index, ObjectConvertor.convertToBytes(attributes));
            } catch (SQLException e) {
                throw new HibernateException(e);
            }
        }
        if (attributes == null) {
            try {
                st.setNull(index, Types.BINARY);
            } catch (SQLException e) {
                throw new HibernateException(e);
            }
        }

    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

    @Override
    public Class returnedClass() {
        return className;
    }

    @Override
    public int[] sqlTypes() {
        return new int[] { Types.BLOB };
    }

}
