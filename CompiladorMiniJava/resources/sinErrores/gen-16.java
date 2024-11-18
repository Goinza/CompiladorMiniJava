///5&6&7&11&exitosamente


class Init{
    static void main()
    { 
    	var i = 5;
        while (i < 10) {
        	debugPrint(i);
        	i += 1;
        	if (i == 8) {
        		break;
        	}
        }
        
        debugPrint(11);
    }
}


