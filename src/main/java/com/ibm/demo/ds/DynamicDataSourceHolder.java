package com.ibm.demo.ds;

import com.ibm.demo.common.Source;

public class DynamicDataSourceHolder {
	public static final ThreadLocal<String> HOLDER = ThreadLocal.withInitial( Source.MASTER::toString );

	public static void set( String source ) {
		HOLDER.set( source );
	}

	public static String get() {
		return HOLDER.get();
	}

	public static void remove() {
		HOLDER.remove();
	}
}