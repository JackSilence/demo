package com.ibm.demo.ds;

import com.ibm.demo.common.Source;

public class DynamicDataSourceHolder {
	public static final ThreadLocal<Source> HOLDER = ThreadLocal.withInitial( () -> Source.WRITE );

	public static void set( Source source ) {
		HOLDER.set( source );
	}

	public static Source get() {
		return HOLDER.get();
	}

	public static void remove() {
		HOLDER.remove();
	}
}