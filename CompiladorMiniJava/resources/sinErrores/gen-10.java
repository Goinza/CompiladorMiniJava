///10&exitosamente


class Init{
	
    static void main()
    { 
    	var obj = new A();
    	var y = obj.x;
    	debugPrint(y);
    }

}

class A {
	
	int x;
	
	public A() {
		x = 10;
	}
	
}