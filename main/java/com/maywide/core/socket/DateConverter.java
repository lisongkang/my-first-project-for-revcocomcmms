package com.maywide.core.socket;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DateConverter implements Converter  {

	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void marshal(Object value, HierarchicalStreamWriter writer,
			MarshallingContext context) {
		// TODO Auto-generated method stub
		Date date = (Date) value;
		writer.setValue(format.format(value));
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader,
			UnmarshallingContext context) {
		try{
			return format.parse(reader.getValue());
		} catch(Exception e){
		}
		return null;
	}

	@Override
	public boolean canConvert(Class arg0) {
		return Date.class.equals(arg0);
	}

}
