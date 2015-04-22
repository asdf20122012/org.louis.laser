# Laser,java序列化框架

欢迎大家吐槽,有什么问题欢迎大家联系493505934@qq.com

Example
==========

```java

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class LaserTest {

	public static void main(String[] args) throws Exception {
		int length = 100;
		Map<String, A> as = new HashMap<String, A>(length);
		List<C> list = new ArrayList<C>(length);
		for (int i = 0; i < length; i++) {
			list.add(new C("a" + i));
			list.add(new C("b" + i));
			list.add(new C("c" + i));
			as.put("a" + i, new A("1"));
			as.put("b" + i, new B("2"));
			as.put("c" + i, new C("3"));
		}
		M m = new M(as, list);
		Laser.laser().registerType(M.class);
		ByteArray array = ByteArray.get();
		long start = System.currentTimeMillis();
		Context context = new Context();
		Laser.laser().writeClassAndObject(context, array, m);
		System.out.println("writeclass=" + (System.currentTimeMillis() - start));
		System.out.println("length=" + array.writePosition());

		context = new Context();
		start = System.currentTimeMillis();
		Laser.laser().readClassAndObject(context, array);
		System.out.println("readclass=" + (System.currentTimeMillis() - start));
		System.out.println("---------------------");
		array.reset();
		start = System.currentTimeMillis();
		context = new Context();
		Laser.laser().writeClassAndObject(context, array, m);
		System.out.println("writeclass=" + (System.currentTimeMillis() - start));
		System.out.println("length=" + array.writePosition());

		context = new Context();
		start = System.currentTimeMillis();
		Laser.laser().readClassAndObject(context, array);
		System.out.println("readclass=" + (System.currentTimeMillis() - start));
	}

	static class M {
		int i = 0;
		Integer j = 1;
		Object list;
		Object as;

		int[] is = new int[] { 1, 2, 3, 4, 5 };

		public M() {
		}

		public M(Map<String, A> as, List<C> list) {
			super();
			this.as = as;
			this.list = list;
		}
	}

	static class A {
		public A() {
		}

		public A(String a) {
			super();
			this.a = a;
		}

		String a;
	}

	static class B extends A {

		public B() {
		}

		public B(String b) {
			super("A" + b);
			this.b = b;
		}

		String b;
	}

	static class C extends A {
		public C() {
		}

		public C(String c) {
			super("C" + c);
			this.c = c;
		}

		String c;
	}

}



```
Outputs:

```
writeclass=11
length=5180
readclass=19

```
