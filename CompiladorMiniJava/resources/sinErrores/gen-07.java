///1&33&5&5&exitosamente


class Init{
	
    static void main()
    { 
    	var x = 33;
    	
    	new A().another();
    	
    	if (x > 10) {
    		debugPrint(x);
    		x = 5;
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
    		debugPrint(1);
    	}
	}
	
}