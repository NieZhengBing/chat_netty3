package com.nzb.netty3.common.core.serial;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jboss.netty.buffer.ChannelBuffer;

public abstract class Serializer {
	public static final Charset CHARSET = Charset.forName("UTF-8");
	protected ChannelBuffer writeBuffer;
	protected ChannelBuffer readBuffer;

	protected abstract void read();

	protected abstract void write();

	public Serializer readFromBytes(byte[] bytes) {
		readBuffer = BufferFactory.getBuffer();
		read();
		readBuffer.clear();
		return this;
	}

	public void readFromBuffer(ChannelBuffer readBuffer) {
		this.readBuffer = readBuffer;
		read();
	}

	public ChannelBuffer writeToLocalBuff() {
		writeBuffer = BufferFactory.getBuffer();
		write();
		return writeBuffer;
	}

	public ChannelBuffer writeToTargetBuff(ChannelBuffer buffer) {
		writeBuffer = buffer;
		write();
		return writeBuffer;
	}

	public byte[] getBytes() {
		writeToLocalBuff();
		byte[] bytes = null;
		if (writeBuffer.writerIndex() == 0) {
			bytes = new byte[0];
		} else {
			bytes = new byte[writeBuffer.writerIndex()];
			writeBuffer.readBytes(bytes);
		}
		writeBuffer.clear();
		return bytes;
	}

	public byte readByte() {
		return readBuffer.readByte();
	}

	public short readShort() {
		return readBuffer.readShort();
	}

	public int readInt() {
		return readBuffer.readInt();
	}

	public long readLong() {
		return readBuffer.readLong();
	}

	public float readFloat() {
		return readBuffer.readFloat();
	}

	public double readDouble() {
		return readBuffer.readDouble();
	}

	public String readString() {
		short size = readBuffer.readShort();
		if (size <= 0) {
			return "";
		}
		byte[] bytes = new byte[size];
		readBuffer.readBytes(bytes);

		return new String(bytes, CHARSET);
	}

	public <T> List<T> readList(Class<T> clz) {
		List<T> list = new ArrayList<T>();
		short size = readBuffer.readShort();
		for (int i = 0; i < size; i++) {
			list.add(read(clz));
		}
		return list;
	}

	public <K, V> Map<K, V> readMap(Class<K> keyClz, Class<V> valueClz) {
		Map<K, V> map = new HashMap<K, V>();
		short size = readBuffer.readShort();
		for (int i = 0; i < size; i++) {
			K key = read(keyClz);
			V value = read(valueClz);
			map.put(key, value);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public <I> I read(Class<I> clz) {
		Object t = null;
		if (clz == int.class || clz == Integer.class) {
			t = this.readInt();
		} else if (clz == byte.class || clz == Byte.class) {
			t = this.readByte();
		} else if (clz == short.class || clz == Short.class) {
			t = this.readShort();
		} else if (clz == long.class || clz == Long.class) {
			t = this.readLong();
		} else if (clz == float.class || clz == Float.class) {
			t = this.readFloat();
		} else if (clz == double.class || clz == Double.class) {
			t = this.readDouble();
		} else if (clz == String.class) {
			t = this.readString();
		} else if (Serializer.class.isAssignableFrom(clz)) {
			byte hasObject = this.readBuffer.readByte();
			try {
				if (hasObject == 1) {
					Serializer temp = (Serializer) clz.newInstance();
					temp.readFromBuffer(this.readBuffer);
					t = temp;
				} else {
					t = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException(String.format("unsupported type: [%s]", clz));
		}

		return (I) t;
	}

	public Serializer writeByte(Byte value) {
		writeBuffer.writeByte(value);
		return this;
	}

	public Serializer writeShort(Short value) {
		writeBuffer.writeShort(value);
		return this;
	}

	public Serializer writeInt(Integer value) {
		writeBuffer.writeInt(value);
		return this;
	}

	public Serializer writeLong(Long value) {
		writeBuffer.writeLong(value);
		return this;
	}

	public Serializer writeFloat(Float value) {
		writeBuffer.writeFloat(value);
		return this;
	}

	public Serializer writeDouble(Double value) {
		writeBuffer.writeDouble(value);
		return this;
	}

	public <T> Serializer writeList(List<T> list) {
		if (isEmpty(list)) {
			writeBuffer.writeShort(0);
			return this;
		}
		writeBuffer.writeShort(list.size());
		for (T item : list) {
			writeObject(item);
		}
		return this;
	}

	public <K, V> Serializer writeMap(Map<K, V> map) {
		if (isEmpty(map)) {
			writeBuffer.writeShort(0);
			return this;
		}
		writeBuffer.writeShort(map.size());
		for (Entry<K, V> entry : map.entrySet()) {
			writeObject(entry.getKey());
			writeObject(entry.getValue());
		}
		return this;
	}

	public Serializer writeString(String value) {
		if (value == null || value.isEmpty()) {
			writeBuffer.writeShort(0);
			return this;
		}
		byte[] data = value.getBytes();
		Short size = (short) data.length;
		writeBuffer.writeShort(size);
		writeBuffer.writeBytes(data);
		return this;
	}

	public Serializer writeObject(Object object) {
		if (object == null) {
			writeByte((byte) 0);
		} else {
			if (object instanceof Integer) {
				writeInt((int) object);
				return this;
			}
			if (object instanceof Long) {
				writeLong((long) object);
				return this;
			}
			if (object instanceof Short) {
				writeShort((short) object);
				return this;
			}
			if (object instanceof Byte) {
				writeByte((byte) object);
				return this;
			}
			if (object instanceof String) {
				writeString((String) object);
				return this;
			}
			if (object instanceof Serializer) {
				writeByte((byte) 1);
				((Serializer) object).writeToTargetBuff(writeBuffer);
				return this;
			}
			throw new RuntimeException("can not serializer type: " + object.getClass());
		}
		return this;
	}

	private <T> boolean isEmpty(Collection<T> c) {
		return c == null || c.size() == 0;
	}

	private <K, V> boolean isEmpty(Map<K, V> c) {
		return c == null || c.size() == 0;
	}

}
