package fusion.comerger.algorithm.merger.holisticMerge.consistency;

public class TestW {

	public static void main(String[] args) {

		// consensus for three opinions
		double b1 = 0.3, d1 = 0.5, u1 = 0.2, a1 = 0.3;
		double b2 = 0.7, d2 = 0.1, u2 = 0.2, a2 = 0.2;
		double b3 = 0.4, d3 = 0.3, u3 = 0.3, a3 = 0.8;
		double t1 = b1 + a1 * u1;
		double t2 = b2 + a2 * u2;
		double t3 = b3 + a3 * u3;
		System.out.println("t o1: " + t1);
		System.out.println("t o2: " + t2);
		System.out.println("t o3: " + t3);

		double k12 = u1 + u2 - u1 * u2;
		double b12 = (b1 * u2 + b2 * u1) / k12;
		double d12 = (d1 * u2 + d2 * u1) / k12;
		double u12 = (u1 * u2) / k12;
		double a12 = (a1 * u2 + a2 * u1 - (a1 + a2) * u1 * u2) / (u1 + u2 - 2 * u1 * u2);

		double k123 = u12 + u3 - u12 * u3;
		double b123 = (b12 * u3 + b3 * u12) / k123;
		double d123 = (d12 * u3 + d3 * u12) / k123;
		double u123 = (u12 * u3) / k123;
		double a123 = (a12 * u3 + a3 * u12 - (a12 + a3) * u12 * u3) / (u12 + u3 - 2 * u12 * u3);

		System.out.println("consesus :: b: " + b123 + " \t d: " + d123 + " \t u: " + u123 + " \t a: " + a123);

		double t = b123 + a123 * u123;
		System.out.println("t: " + t);

	}

}
