///2&3&4&exitosamente


class Init{
	
    static void main()
    { 
    	A.m1();
    }

}

class A {
	
	static int a1;
	
	static void m1() {
		a1 = 2;
		debugPrint(a1);
		while (a1 < 5) {
    		debugPrint(a1);
    		a1 = a1 + 1;
    	}
	}
	
}