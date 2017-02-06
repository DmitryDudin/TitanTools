package org.titantech.titantools.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class ClassAndObjectUtils {

    public static byte[] compress(byte[] bytesToCompress) throws IOException {
        // Compressor with highest level of compression.
        Deflater compressor = new Deflater(Deflater.BEST_COMPRESSION);
        compressor.setInput(bytesToCompress); // Give the compressor the data to compress.
        compressor.finish();
        // Create an expandable byte array to hold the compressed data.
        // It is not necessary that the compressed data will be smaller than
        // the uncompressed data.
        ByteArrayOutputStream bos = new ByteArrayOutputStream(
                bytesToCompress.length);
        // Compress the data
        byte[] buf = new byte[bytesToCompress.length + 100];
        while (!compressor.finished()) {
            bos.write(buf, 0, compressor.deflate(buf));
        }
        bos.close();
        // Get the compressed data
        return bos.toByteArray();
    }

    public static byte[] decompress(byte[] compressedBytes) throws IOException, DataFormatException {
        // Initialize decompressor.
        Inflater decompressor = new Inflater();
        decompressor.setInput(compressedBytes); // Give the decompressor the data to decompress.
        decompressor.finished();
        // Create an expandable byte array to hold the decompressed data.
        // It is not necessary that the decompressed data will be larger than
        // the compressed data.
        ByteArrayOutputStream bos = new ByteArrayOutputStream(compressedBytes.length);
        // Decompress the data
        byte[] buf = new byte[compressedBytes.length + 100];
        while (!decompressor.finished()) {
            bos.write(buf, 0, decompressor.inflate(buf));
        }
        bos.close();
        // Get the decompressed data.
        return bos.toByteArray();
    }

    public static Object deserialize(byte[] ser) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(ser);
        ObjectInputStream ois = new ObjectInputStream(bais);
        Object retval = ois.readObject();
        return retval;
    }

    public static byte[] serialize(Serializable ser) throws IOException {
        if (ser == null) return new byte[0];
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(ser);
        byte[] retval = baos.toByteArray();
        return retval;
    }
}