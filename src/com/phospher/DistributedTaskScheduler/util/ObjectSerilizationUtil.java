package com.phospher.DistributedTaskScheduler.util;

import java.io.*;

public class ObjectSerilizationUtil {

	public static void writeObject(DataOutput out, Object obj) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(obj);
		out.write(baos.toByteArray());
	}

	public static Object readObject(DataInput in) throws IOException {
		ByteArrayOutputStream baout = new ByteArrayOutputStream();
		while(true) {
			try {
				baout.write(in.readByte());
			} catch(EOFException ex) {
				break;
			}
		}
		
		try {
			ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(baout.toByteArray()));
			return oin.readObject();
		} catch(ClassNotFoundException ex) {
			throw new IOException(ex);
		}
	}
}