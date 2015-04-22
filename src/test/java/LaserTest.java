import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.louis.laser.Context;
import org.louis.laser.Laser;
import org.louis.laser.io.ByteArray;

public class LaserTest {

	public static void main(String[] args) throws Exception {
		int length = 100000;
		Map<String, A> as = new HashMap<String, A>(length);
		List<A> list = new ArrayList<A>(length);
		for (int i = 0; i < length; i++) {
			list.add(new A("a" + i));
			list.add(new B("b" + i));
			list.add(new C("c" + i));
			as.put("a" + i, new A("1"));
			as.put("b" + i, new B("2"));
			as.put("c" + i, new C("3"));
		}
		M m = new M(as, list);
		int count = 10;
		Laser.laser().registerType(M.class);
		long wTotal = 0, rTotal = 0;
		for (int i = 0; i < count; i++) {
			ByteArray array = ByteArray.get();
			long start = System.currentTimeMillis();
			Context context = new Context();
			Laser.laser().writeClassAndObject(context, array, m);
			long w = System.currentTimeMillis() - start;
			wTotal += w;
			System.out.println("w=" + w);
			array.writeTo(new FileOutputStream("/server/1.txt")).close();

			context = new Context();
			// array.readFrom(new FileInputStream("/server/1.txt"));
			start = System.currentTimeMillis();
			Laser.laser().readClassAndObject(context, array);
			long r = System.currentTimeMillis() - start;
			rTotal += r;
			System.out.println("r=" + r);
			array.reset();
			System.out.println("********************************");
		}
		System.out.println(wTotal / count);
		System.out.println(rTotal / count);
	}

	static class M {
		int i = 3;
		Integer j = 1;
		Object list;
		Object as;
		Class<?> t1 = int.class;
		Class<?> t2 = Integer.class;

		int[] is = new int[] { 1, 2, 3, 4, 5 };

		public M() {
		}

		public M(Map<String, A> as, List<A> list) {
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
