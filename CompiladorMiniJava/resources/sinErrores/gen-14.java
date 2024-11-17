///2&2&3&4&exitosamente


class Init{
	
    static void main()
    { 
    	var obj = new A();
    	obj.m1();
    }

}

class A {
	
	int a1;
	
	void m1() {
		a1 = 2;
		debugPrint(a1);
		while (a1 < 5) {
    		debugPrint(a1);
    		a1 = a1 + 1;
    	}
	}
	
}