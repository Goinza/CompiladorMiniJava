///10&33&exitosamente


class Init{
	
    static void main()
    { 
    	var x = 33;
    	var obj = new A();
    	
    	obj.another();
    	
    	if (x > 10) {
    		debugPrint(x);
    	}
    	
    	if (x == 5) {
    		debugPrint(x);
    	}
    }

}

class A {
	
	int y;
	
	void another() {
		y = 10;
    	
    	if (y < 10) {
    		debugPrint(y);
    	}
    	
    	if (y > 0) {
    		debugPrint(y);
    	}
	}
	
}